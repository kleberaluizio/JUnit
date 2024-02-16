package com.telusko.learning;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArraysTest
{
	SortingArray sa = new SortingArray();

	@Test
	@DisplayName("Verify if array is sort correctly")
	void shouldReturnCorrectResultWhenProvidedArrayIsCorrect(){
		// Arrange
		int[] expected = {2,4,6,8};
		int[] unsorted = {4, 8, 6, 2};

			// Act
		int[] actual = sa.sortingArray(unsorted);

		//Assert
		Assertions.assertArrayEquals(expected, actual);
	}

	@Test
	@DisplayName("Assert exception is generated when array is null")
	void testSortingArray_Exception(){
			int[] unsorted = null;
			assertThrows(NullPointerException.class, ()-> sa.sortingArray(unsorted),"Exception not generated!");
	}

}