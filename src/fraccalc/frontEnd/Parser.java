package fraccalc.frontEnd;

import java.util.Scanner;
import java.util.Random;
import fraccalc.backEnd.Fraction;
import fraccalc.backEnd.NotAFractionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {

    public static void main(String[] args) {
        Scanner shreyas = new Scanner(System.in);
        Random rayman = new Random();
        String input;
        Fraction output;
        do {
            System.out.println("Please enter an expression: ");
            input = shreyas.nextLine();
            if (input.compareToIgnoreCase("test") == 0) {
                test();
                continue;
            }
            output = parse(input);
            if (output != null) {
                System.out.println(output.toString());
            }
        } while ((input.compareToIgnoreCase("quit") != 0) && (input.compareToIgnoreCase("exit") != 0));
        System.out.println("Bye!");

    }

    /**
     * Parses then evaluates the string given, then returns it as a fraction
     * <p>
     * The method puts together
     *
     * @param input The string to be parsed
     * @return The fraction that is the evaluated expression
     */
    public static Fraction parse(String input) {
        //input = input.replaceAll("\\s+","");
        int openParentheses = input.indexOf("(");
        int closeParentheses = input.indexOf(")");
        //recursively getting rid of what's in parenthathes
        while (openParentheses != -1) {
            input = input.substring(0, openParentheses) + parse(input.substring(openParentheses+1, closeParentheses)).toString() + input.substring(closeParentheses + 1);
            openParentheses = input.indexOf("(");
            closeParentheses = input.indexOf(")");
        }
        /*ArrayList<Integer> pluses = new ArrayList();
         ArrayList<Integer> minuses = new ArrayList();
         ArrayList<Integer> timeses = new ArrayList();
         ArrayList<Integer> divides = new ArrayList();
         ArrayList<Integer> operators = new ArrayList();
         for(int i = 0; i < input.length(); i++){
         switch(input.charAt(i)){
         case '+':
         pluses.add(i);
         operators.add(i);
         break;
         case '-':
         minuses.add(i);
         operators.add(i);
         break;
         case '*':
         timeses.add(i);
         operators.add(i);
         break;
         case '/':
         divides.add(i);
         operators.add(i);
         break;
         }
         }
         String working;
         if(operators.isEmpty())
         try {
         return new Fraction(input);
         } catch (NotAFractionException ex) {
         System.out.println(ex.reason);
         }
         while(!timeses.isEmpty()){
         working = input.substring(0,operators.get(timeses.get(0)-1));
         try {
         working += Fraction.multiply(new Fraction(input.substring(operators.get(timeses.get(0)-1))),new Fraction(input.substring(operators.get(timeses.get(0)+1))));
         } catch (NotAFractionException ex) {
         System.out.println(ex.reason);
         return Fraction.zero;
         }
         working += input.substring(operators.get(timeses.get(0)+1));
         timeses.remove(0);
         input = working;
         }while(!divides.isEmpty()){
         working = input.substring(0,operators.get(divides.get(0)-1));
         try {
         working += Fraction.divide(new Fraction(input.substring(operators.get(divides.get(0)-1))),new Fraction(input.substring(operators.get(divides.get(0)+1))));
         } catch (NotAFractionException ex) {
         System.out.println(ex.reason);
         return null;
         }
         working += input.substring(operators.get(divides.get(0)+1));
         divides.remove(0);
         input = working;
         }while(!pluses.isEmpty()){
         working = input.substring(0,operators.get(pluses.get(0)-1));
         try {
         working += Fraction.divide(new Fraction(input.substring(operators.get(pluses.get(0)-1))),new Fraction(input.substring(operators.get(pluses.get(0)+1))));
         } catch (NotAFractionException ex) {
         System.out.println(ex.reason);
         return null;
         }
         working += input.substring(operators.get(pluses.get(0)+1));
         pluses.remove(0);
         input = working;
         }while(!minuses.isEmpty()){
         working = input.substring(0,operators.get(minuses.get(0)-1));
         try {
         working += Fraction.divide(new Fraction(input.substring(operators.get(minuses.get(0)-1))),new Fraction(input.substring(operators.get(minuses.get(0)+1))));
         } catch (NotAFractionException ex) {
         System.out.println(ex.reason);
         return null;
         }
         working += input.substring(operators.get(minuses.get(0)+1));
         minuses.remove(0);
         input = working;
         }*/

        ArrayList<String> expressions = new ArrayList<String>(Arrays.asList(input.split(" ")));
        try {
            for (int i = 1; i < expressions.size() - 1; i += 2) {
                if (expressions.get(i).equals("*")) {
                    expressions.add(i + 2, Fraction.multiply(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                    expressions.remove(i + 1);
                    expressions.remove(i);
                    expressions.remove(i - 1);
                }
                if (expressions.size() == 1) {
                    break;
                }
                if (expressions.get(i).equals("/")) {
                    expressions.add(i + 2, Fraction.divide(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                    expressions.remove(i + 1);
                    expressions.remove(i);
                    expressions.remove(i - 1);
                }
                if (expressions.size() == 1) {
                    break;
                }
            }
            for (int i = 1; i < expressions.size() - 1; i += 2) {
                if (expressions.size() == 1) {
                    break;
                }
                if (expressions.get(i).equals("+")) {
                    expressions.add(i + 2, Fraction.add(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                    expressions.remove(i + 1);
                    expressions.remove(i);
                    expressions.remove(i - 1);
                }
                if (expressions.size() == 1) {
                    break;
                }
                if (expressions.get(i).equals("-")) {
                    expressions.add(i + 2, Fraction.subtract(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                    expressions.remove(i + 1);
                    expressions.remove(i);
                    expressions.remove(i - 1);
                }
                if (expressions.size() == 1) {
                    break;
                }
            }
            if (expressions.size() != 1) {
                System.out.println("This isn't a valid expression");
                return null;
            }else{
                input = expressions.get(0);
            }
        } catch (NotAFractionException ex) {
            System.out.println(ex.reason);
            return null; 
        }
        try {
            Fraction output = new Fraction(input);
            output.format();
            return output;
        } catch (NotAFractionException ex) {
            System.out.println(ex.reason);
            return null;
        }
    }

    public static void test() {
        try {
            Fraction test1 = new Fraction(1, 0, 1);

            Fraction test4 = new Fraction("1_2/3");
            Fraction test5 = new Fraction("1");
            Fraction test6 = new Fraction("2/3");
            System.out.println(test1.toString());
            System.out.println(test4.toString());
            System.out.println(test5.toString());
            System.out.println(test6.toString());
            System.out.println(Fraction.multiply(test6, test5).toString());
            System.out.println(Fraction.divide(test1, test4).toString());
            Fraction test2 = new Fraction(1, 0, 0);
            System.out.println(test2.toString());
            Fraction test3 = new Fraction("Hello world");


            /*Fraction test3 = new Fraction("Hello world");
             Fraction test7 = new Fraction("2/0");
             System.out.println(test3.toString());
             System.out.println(test7.toString());*/
        } catch (NotAFractionException e) {
            System.out.println(e.reason);
        } finally {

        }
    }
}
