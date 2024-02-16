package com.telusko.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ShapesTest
{
	Shapes s = new Shapes();

	@Nested
	@DisplayName("Compute Square Area Tests")
	class ComputeSquareAreaTests {
		@Test
		@DisplayName("Calcula a área de um quadrado")
		void shouldReturnCorrectResultWhenProvidedValueIsCorrect()
		{
			// arrange
			double expected = Math.pow(3.3,2);

			//act
			double actual = s.computeSquareArea(3.3);

			// assert
			Assertions.assertEquals(expected,actual);
		}
	}

	@Nested
	@DisplayName("Compute Circle Area Tests")
	class ComputeCircleAreaTests{
		@Test
		@DisplayName("Calcula a área de um Círculo")
		void shouldReturnCorrectResultWhenProvidedValueIsCorrect()
		{
			// arrange
			double expected = Math.PI * 4 * 4;

			//act
			double actual = s.computeCircleArea(4);

			// assert
			Assertions.assertEquals(expected,actual, "Error Message, it's like a printstacktrace");
		}
	}

}