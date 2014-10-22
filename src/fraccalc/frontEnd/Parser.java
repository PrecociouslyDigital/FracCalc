package fraccalc.frontEnd;

import java.util.Scanner;
import java.util.Random;
import fraccalc.backEnd.Fraction;
import fraccalc.backEnd.NotAFractionException;

public class Parser {

	public static void main(String[] args) {
		Scanner shreyas = new Scanner(System.in);
                Random rayman = new Random();
		String input;
		do{
			System.out.println("Please enter an expression: ");
		 	input = shreyas.nextLine();
		 	parse(input);
                        if(input.compareToIgnoreCase("test") == 0)
                            test();
		}
		while((input.compareToIgnoreCase("quit") != 0) &&(input.compareToIgnoreCase("exit") != 0));
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
        public static void test(){
    try{
           Fraction test1 = new Fraction(1,0,1);
            
            Fraction test4 = new Fraction("1_2/3");
            Fraction test5 = new Fraction("1");
            Fraction test6 = new Fraction("2/3");
            System.out.println(test1.toString());
            System.out.println(test4.toString());
            System.out.println(test5.toString());
            System.out.println(test6.toString());
            System.out.println(Fraction.multiply(test6, test5).toString());
            System.out.println(Fraction.divide(test1, test4).toString());
                        Fraction test2 = new Fraction(1,0,0);
                                    System.out.println(test2.toString());
            Fraction test3 = new Fraction("Hello world");


                        /*Fraction test3 = new Fraction("Hello world");
                                    Fraction test7 = new Fraction("2/0");
                                                System.out.println(test3.toString());
                                                            System.out.println(test7.toString());*/
    }catch(NotAFractionException e){
        System.out.println(e.reason);
    }finally{
    
}
}
}
