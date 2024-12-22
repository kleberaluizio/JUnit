package barriga.domain.builders;

import barriga.domain.Account;
import barriga.domain.Transaction;
import barriga.domain.User2;

import static java.lang.String.format;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Class responsible for entities builders creation
 *
 * @author Kleber Aluizio
 *
 */
public class BuilderMaster {

    Set<String> importList = new HashSet<>();
    static String packageName = BuilderMaster.class.getPackage().getName();

    @SuppressWarnings("rawtypes")
    public void createBuilderClass(Class clazz) {

        String className = clazz.getSimpleName() + "Builder";

        StringBuilder builder = new StringBuilder();

        builder.append(format("public class %s {\n", className));
        List<Field> declaredFields = getClassFields(clazz).stream()
                .filter(field -> !field.getName().equals("serialVersionUID") && !Modifier.isStatic(field.getModifiers()))
                .toList();
        declaredFields.forEach(field -> {
            if (field.getType().getSimpleName().equals("List"))
                builder.append(format("\tprivate %s<%s> %s;\n", field.getType().getSimpleName(), getGenericSimpleName(field), field.getName()));
            else
                builder.append(format("\tprivate %s %s;\n", field.getType().getSimpleName(), field.getName()));
        });

        builder.append(format("\n\tprivate %s(){}\n\n", className));

        builder.append(format("\tpublic static %s a%s() {\n", className, clazz.getSimpleName()));
        builder.append(format("\t\t%s builder = new %s();\n", className, className));
        builder.append("\t\tinitializeDefaultData(builder);\n");
        builder.append("\t\treturn builder;\n");
        builder.append("\t}\n\n");

        builder.append(format("\tprivate static void initializeDefaultData(%s builder) {\n", className));
        declaredFields.forEach(field -> builder.append(format("\t\tbuilder.%s = %s;\n", field.getName(), getDefaultParameter(field))));
        builder.append("\t}\n\n");

        for (Field field : declaredFields) {
            registerImports(field.getType().getCanonicalName());
            if (field.getType().getSimpleName().equals("List")) {
                registerImports("java.util.Arrays");
                builder.append(format("\tpublic %s withList%s%s(%s... %s) {\n",
                        className, field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1), getGenericSimpleName(field), field.getName()));
                builder.append(format("\t\tthis.%s = Arrays.asList(%s);\n", field.getName(), field.getName()));
            } else {
                builder.append(format("\tpublic %s with%s%s(%s %s) {\n",
                        className, field.getName().substring(0, 1).toUpperCase(), field.getName().substring(1), field.getType().getSimpleName(), field.getName()));
                builder.append(format("\t\tthis.%s = %s;\n", field.getName(), field.getName()));
            }
            builder.append("\t\treturn this;\n");
            builder.append("\t}\n\n");
        }

        builder.append(format("\tpublic %s now() {\n", clazz.getSimpleName()));
        builder.append(format("\t\treturn new %s(", clazz.getSimpleName()));
        boolean first = true;
        for (Field field : declaredFields) {
            if(first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("this.").append(field.getName());
        }
        builder.append(");\n\t}\n");

        builder.append("}");

        StringBuilder rawBuilderClass = new StringBuilder();
        rawBuilderClass.append("package ").append(packageName).append(";\n\n");

        for (String str : importList) {
            if(!str.contains("java.lang."))
                rawBuilderClass.append(str).append("\n");
        }

        rawBuilderClass.append("import ").append(clazz.getCanonicalName()).append(";\n\n");
        rawBuilderClass.append(builder);

        writeToFile(className, String.valueOf(rawBuilderClass));
    }

    @SuppressWarnings("rawtypes")
    private String getGenericSimpleName(Field field) {
        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        return ((Class) stringListType.getActualTypeArguments()[0]).getSimpleName();
    }

    @SuppressWarnings("rawtypes")
    public List<Field> getClassFields(Class clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            List<Field> fieldsSC = Arrays.asList(superClass.getDeclaredFields());
            fields.addAll(fieldsSC);
        }
        return fields;
    }

    public String getDefaultParameter(Field field) {
        String type = field.getType().getSimpleName();
        if (type.equals("int") || type.equals("Integer")) {
            return "0";
        }
        if (type.equalsIgnoreCase("long")) {
            return "0L";
        }
        if (type.equalsIgnoreCase("double") || type.equalsIgnoreCase("float")) {
            return "0.0";
        }
        if (type.equalsIgnoreCase("boolean")) {
            return "false";
        }
        if (type.equals("String")) {
            return "\"\"";
        }
        return "null";
    }

    public void registerImports(String clazz) {
        if (clazz.contains("."))
            importList.add("import " + clazz + ";");
    }

    private static void writeToFile(String builderClassName, String content) {
        String directoryPath = "src/test/java/" + packageName.replace(".", "/");
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, builderClassName + ".java")))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BuilderMaster master = new BuilderMaster();
        master.createBuilderClass(Transaction.class);
    }
}
