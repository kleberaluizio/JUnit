package com.telusko.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class ShapesTest
{
	static Shapes s;

	@BeforeAll
	static void init(){
		s = new Shapes();
		System.out.println("Init tests!");
	}

	@Nested
	@DisplayName("Square Area Tests")
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
	@DisplayName("Circle Area Tests")
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
	@AfterAll
	static void finish(){
		System.out.println("Finished all tests!");
	}

}