package com.telusko.learning;

public class LaunchCalc
{
	public static void main(String[] args)
	{
		Calculator c = new Calculator();

		int result = c.divide(10,5);

		if(result == 2){
			System.out.println("Test case has passed!");
		} else {
			System.out.println("Test case hasn't passed!");
		}
		int result2 = c.divide(10,0);

		if(result2 == 2){
			System.out.println("Test case has passed!");
		} else {
			System.out.println("Test case hasn't passed!");
		}
	}
}