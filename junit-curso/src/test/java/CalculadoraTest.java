
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculadoraTest
{

	private static final Calculadora calculadora = new Calculadora();

	@Test
	@DisplayName("Should return valid result when valid input")
	void testSoma_shouldReturnValidResultWhenValidInput() {
		// Arrange - GIVEN
		int a = 5;
		int b = 2;

		// Act - WHEN
		int result = calculadora.soma(a, b);
		int EXPECTED_RESULT = 7;

		// Assert - THEN
		Assertions.assertEquals(EXPECTED_RESULT, result);
	}

	@Test
	@DisplayName("Should return correct value when valid input")
	void testDividir_shouldReturnCorrectIntegerValueWhenValidInput() {
		// Arrange
		int numerador = 10;
		int denominador = 2;

		// Act
		float ACTUAL_RESULT = calculadora.dividir(numerador, denominador);
		float EXPECTED_RESULT = 5;

		// Assert
		Assertions.assertEquals(EXPECTED_RESULT, ACTUAL_RESULT);
	}

	@Test
	@DisplayName("Should return correct negative value when valid input")
	void testDividir_shouldReturnCorrectNegativeValueWhenValidInput() {
		// Arrange
		int numerador = 10;
		int denominador = -2;

		// Act
		float ACTUAL_RESULT = calculadora.dividir(numerador, denominador);
		float EXPECTED_RESULT = -5;

		// Assert
		Assertions.assertEquals(EXPECTED_RESULT, ACTUAL_RESULT);
	}

	@Test
	@DisplayName("Should return correct float value when valid input")
	void testDividir_shouldReturnCorrectFloatValueWhenValidInput() {
		// Arrange
		int numerador = 10;
		int denominador = 3;

		// Act
		float ACTUAL_RESULT = calculadora.dividir(numerador, denominador);
		float EXPECTED_RESULT = 3.333f;

		// Assert
		Assertions.assertEquals(EXPECTED_RESULT, ACTUAL_RESULT, 0.001);
	}

	@Test
	@DisplayName("Should throws exception when den is Zero ")
	void testDividir_shoudlThrowExceptionWhenDenIsZero()
	{
		// Arrange
		int numerador = 10;
		int denominador = 0;

		// Act and  Assert
		ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class,
			() -> calculadora.dividir(numerador, denominador));
		Assertions.assertEquals("/ by zero", exception.getMessage());
	}

	@Test
	@DisplayName("Should throws exception when den is Zero - JUnit4 ")
	void testDividir_shoudlThrowExceptionWhenDenIsZeroJUnit4()
	{
		// Arrange
		int numerador = 10;
		int denominador = 0;

		// Act and  Assert
		try {
			float result = calculadora.dividir(numerador, denominador);
			Assertions.fail("Should throw exception");
		} catch (ArithmeticException e){
			Assertions.assertEquals("/ by zero", e.getMessage());
		}
	}

	@Test
	void assertivas() {
		Assertions.assertEquals("casa", "casa");
		Assertions.assertNotEquals("Casa", "casa");
		Assertions.assertTrue("casa".equalsIgnoreCase("CASA"));

		List<String> s1 = new ArrayList<>();
		List<String> s2 = new ArrayList<>();
		List<String> s3 = null;

		Assertions.assertEquals(s1, s2);
		Assertions.assertSame(s1, s1); // Verifica a referência dos objetos.
		Assertions.assertNotEquals(s1, s3); // Verifica a referência dos objetos.
		Assertions.assertNull(s3);
		Assertions.assertNotNull(s1);
		Assertions.assertThrows(NullPointerException.class, ()-> s3.equals(s1));
	}
}