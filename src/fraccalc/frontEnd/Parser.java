package fraccalc.frontEnd;

import java.util.Scanner;

import fraccalc.backEnd.Fraction;

public class Parser {

	public static void main(String[] args) {
		Scanner shreyas = new Scanner(System.in);
		String input;
		do{
			System.out.println("Please enter an expression: ")
		 	input = shreyas.nextLine();
		 	parse(input);
		}
		while(!input.equals("quit"));
	}

	public static String parse(String carolina){
		
		return null;
	}
}
