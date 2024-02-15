package com.telusko.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShapesTest
{

	@Test
	@DisplayName("Calcula a área de um quadrado")
	void shouldReturnCorrectResultWhenProvidedValueIsCorrect()
	{
		// arrange
		Shapes s = new Shapes();
		double expected = Math.pow(3.3,2);

		//act
		double actual = s.computeSquareArea(3.3);

		// assert
		Assertions.assertEquals(expected,actual);
	}
}