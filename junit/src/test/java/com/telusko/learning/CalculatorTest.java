package com.telusko.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class CalculatorTest
{

	@Nested
	@DisplayName("Division Tests")
	class DivisionTests
	{
		@Test
		@DisplayName("Should return correct result when valid values are provided")
		void shouldReturnCorrectResultWhenValidValuesProvided()
		{
			// arrange
			Calculator c = new Calculator();
			int expected = 2;

			// act
			int actual = c.divide(10, 5);

			// assert
			Assertions.assertEquals(expected, actual);
		}

		@Test
		@DisplayName("Should throw exception when denominator is equals zero")
		void shouldThrowExceptionWhenDenominatorEqualsZero()
		{
			// arrange
			Calculator c = new Calculator();

			// act & assert
			Assertions.assertThrows(ArithmeticException.class, () -> c.divide(10, 0));
		}
	}

	@Nested
	@DisplayName("Multiplication Tests")
	class MultiplicationTests{
		@Test
		@DisplayName("Should return correct result when valid values are provided")
		void shouldReturnCorrectResultWhenValidValuesProvided()
		{
			// arrange
			Calculator c = new Calculator();
			int expected = 50;

			// act
			int actual = c.multiply(10,5);

			// assert
			Assertions.assertEquals(expected, actual);
		}
	}


}