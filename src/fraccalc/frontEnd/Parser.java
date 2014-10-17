package fraccalc.frontEnd;

import java.util.Scanner;
import java.util.Random;
import fraccalc.backEnd.Fraction;

public class Parser {

	public static void main(String[] args) {
		Scanner shreyas = new Scanner(System.in);
                Random rayman = new Random();
		String input;
		do{
			System.out.println("Please enter an expression: ");
		 	input = shreyas.nextLine();
		 	parse(input);
		}
		while((input.compareToIgnoreCase("quit") != 0) &&(input.compareToIgnoreCase("exit") != 0) );
                System.out.println("Bye!");
                
	}
        /**
        *Parses then evaluates the string given, then returns it as a fraction
        *<p>
        *The method puts together 
        *@param input The string to be parsed
        * @return The fraction that is the evaluated expression
        */
	public static Fraction parse(String input){
		
		return null;
	}
}
