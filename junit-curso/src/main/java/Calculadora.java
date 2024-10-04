public class Calculadora
{
	public int soma(int a, int b){
		return Math.addExact(a, b);
	}

	public float dividir(int num, int den) {
		if (den == 0) {
			throw new ArithmeticException("/ by zero");
		}
		return (float) num / den;
	}

}
