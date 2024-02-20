package com.telusko.learning;

import java.util.Arrays;

public class SortingArray
{
	public int[] sortingArray(int[] array){
		Arrays.sort(array);
		return array;
	}

	public int[] sortingArrayP(int[] array){
		for(int i = 0; i< 1000000; i++){
			Arrays.sort(array);
		}
		return array;
	}
}
