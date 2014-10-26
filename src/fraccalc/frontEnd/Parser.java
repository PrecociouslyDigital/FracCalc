package fraccalc.frontEnd;

import java.util.Scanner;
import fraccalc.backEnd.Fraction;
import fraccalc.backEnd.NotAFractionException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input;
        Fraction output;
        while (true) {
            System.out.println("Please enter an expression: ");
            input = console.nextLine();
            if (input.compareToIgnoreCase("quit") == 0 || input.compareToIgnoreCase("exit") == 0) {
                System.out.println("Bye!");
                console.close();
                System.exit(0);
            } else if (input.compareToIgnoreCase("test") == 0) {
                test();
            } else if(input.compareToIgnoreCase("help") == 0){
                help();
            }else {
                output = parse(input);
                if (output != null) {
                    System.out.println(output.toString());
                }
            }

        }

    }
    /**
     * Adds a help command to the program.
     * */
    public static void help(){
            File file = new File("help.txt");
            Scanner in = new Scanner(file);
            while(in.hasNextLine(){
                System.out.println(in.nextLine());        
            }    
        
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
        input = input.trim();
        Fraction output = null;
        try {
            if (input.indexOf('(') != -1) {
                input = processParentheses(input);
            }
            output = evaluate(input);

        } catch (NotAFractionException ex) {
            System.out.println(ex);
            return null;
        }
        return output;
    }

    /**
     * Method that processes the parentheses in an expression
     *
     * @param input
     * @return Expression with the parentheses evaluated
     * @throws NotAFractionException
     */
    public static String processParentheses(String input) throws NotAFractionException {
        //input = input.replaceAll("\\s+","");
        int openParentheses = input.indexOf("(");
        int closeParentheses = -1;
        String stored;
        //recursively getting rid of what's in parenthathes
        while (openParentheses != -1) {
            boolean found = false;
            int parenthesesCount = 1;
            for (int i = openParentheses + 1; i < input.length(); i++) {
                switch (input.charAt(i)) {
                    case '(':
                        parenthesesCount++;
                        break;
                    case ')':
                        if (--parenthesesCount == 0) {
                            closeParentheses = i;
                            found = true;
                            break;
                        }
                }//break after finding matched parentheses
                if (found) {
                    break;
                }
            }

            stored = "";
            if (openParentheses != 0) {
                stored += input.substring(0, openParentheses);
            }
            stored += parse(input.substring(openParentheses + 1, closeParentheses)).toString();
            if (stored.isEmpty()) {
                break;
            }
            if (closeParentheses < input.length()) {
                stored += input.substring(closeParentheses + 1);
            }
            input = stored;
            openParentheses = input.indexOf("(");
        }

        return input;
    }

    /**
     * Evaluates an expression without parentheses
     *
     * @param input
     * @return evaluated Fraction.
     */
    public static Fraction evaluate(String input) {
        ArrayList<String> expressions = new ArrayList<String>(Arrays.asList(input.split(" ")));
        expressions.removeAll(Arrays.asList(null, ""));
        try {
            for (int i = 1; i < expressions.size() - 1; i += 2) {
                while (true) {
                    if (expressions.get(i).equals("*")) {
                        expressions.add(i + 2, Fraction.multiply(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                        expressions.remove(i + 1);
                        expressions.remove(i);
                        expressions.remove(i - 1);
                        if (expressions.size() == 1 || i > expressions.size() - 1) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (expressions.size() == 1 || i > expressions.size() - 1) {
                    break;
                }
                while (true) {
                    if (expressions.get(i).equals("/")) {
                        expressions.add(i + 2, Fraction.divide(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                        expressions.remove(i + 1);
                        expressions.remove(i);
                        expressions.remove(i - 1);
                        if (expressions.size() == 1 || i > expressions.size() - 1) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            for (int i = 1; i < expressions.size() - 1; i += 2) {
                if (expressions.size() == 1) {
                    break;
                }
                while (true) {
                    if (expressions.get(i).equals("+")) {

                        expressions.add(i + 2, Fraction.add(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                        expressions.remove(i + 1);
                        expressions.remove(i);
                        expressions.remove(i - 1);
                        if (expressions.size() == 1 || i > expressions.size() - 1) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (expressions.size() == 1 || i > expressions.size() - 1) {
                    break;
                }
                while (true) {
                    if (expressions.get(i).equals("-")) {
                        expressions.add(i + 2, Fraction.subtract(new Fraction(expressions.get(i - 1)), new Fraction(expressions.get(i + 1))).toString());
                        expressions.remove(i + 1);
                        expressions.remove(i);
                        expressions.remove(i - 1);
                        if (expressions.size() == 1 || i > expressions.size() - 1) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (expressions.size() != 1) {
                System.out.println("This isn't a valid expression");
                return null;
            } else {
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

    /**
     * Runs all the tests for the fraction program.
     */
    private static void test() {
        //Some basic tests, to make sure fractions are being parsed correctly
        testAgainst("1", "1");
        testAgainst("0", "0");
        //Making sure basic operators are working
        testAgainst("1 + 2", "3");
        testAgainst("1_2/3 + 1_2/3", "3_1/3");
        //Making sure order of operations works
        testAgainst("1 + 2 * 2", "5");
        testAgainst("1/2 + 1/4 * 2", "1");
        //Making sure parentheses work
        testAgainst("2 * (1 + 3)", "8");
        testAgainst("2_2/3 * (1/2 + 1_1/2)", "5_1/3");
        //Making sure nested parentheses work
        testAgainst("((1 + 3) * (1 + 2)) * 2", "24");
        testAgainst("((1_1/2 + 3_1/2) + (2_2/3 + 1_1/3)) * (2_2/3 - 2)", "6");
    }

    /**
     * Checks an expression against its expected value.
     *
     * @param expression Expression to be tested
     * @param expected Expected result
     * @return Whether the test succeeded or failed
     *
     */
    private static void testAgainst(String expression, String expected) {
        if (!expected.equals(parse(expression).toString())) {
            System.out.println("Evaluating " + expression + " failed! Returned " + parse(expression).toString());
        } else {
            System.out.println(expression + " evaluated correctly");
        }
    }
    /*private static void test() {
     //making sure that the fractions are parsed correctly
     Fraction test1;
     Fraction test2;
     Fraction test3;
     Fraction test4;
     Fraction test5;
     Fraction test6;
     try {
     test1 = new Fraction(1, 0, 1);
     test2 = new Fraction("1_2/3");
     test3 = new Fraction("1");
     test4 = new Fraction("2/3");
     System.out.println(test1.toString());
     System.out.println(test2.toString());
     System.out.println(test3.toString());
     System.out.println(test4.toString());
     System.out.println(Fraction.multiply(test4, test3).toString());
     System.out.println(Fraction.divide(test1, test2).toString());
     test5 = new Fraction(1, 0, 0);
     System.out.println(test5.toString());
     } catch (NotAFractionException e) {
     System.out.println(e.reason);
     test1 = null;
     test2 = null;
     test3 = null;
     test4 = null;
     test5 = null;
     test6 = null;
     }
     //Checking to make sure that operations behave correctly

     }

     private static String testOperators(Fraction fraction, Fraction frac, char operator) {
     String returned = "Applying " + operator + " yields ";
     switch (operator) {
     case '+':
     returned += Fraction.add(fraction, frac).toString();
     break;
     case '-':
     returned += Fraction.subtract(fraction, frac).toString();
     break;
     case '/':
     returned += Fraction.divide(fraction, frac).toString();
     break;
     case '*':
     returned += Fraction.multiply(fraction, frac).toString();
     break;
     }
     return returned;
     }

     /*private static void operatorTest(Fraction[][] inputs, String[] expecteds) {
     for (int i = 0; i < inputs.length; i++) {
     if (!testOperators(inputs[1][i], inputs[2][i], '+').equals("2_2/3")) {
     System.out.println("Addition is broken! " + inputs[1][i].toString() + "+" + test[2][i].toString() + " appears to equal " + testOperators(test1, test2, '+'));
     return;
     } else if (!testOperators(test1, test2, '-').equals("0")) {
     System.out.println("Subtraction is broken! " + test1.toString() + "-" + test2.toString() + " appears to equal " + testOperators(test1, test2, '-'));
     } else if (!testOperators(test1, test2, '*').equals("2_7/9")) {
     System.out.println("Multiplication is broken! " + test1.toString() + "-" + test2.toString() + " appears to equal " + testOperators(test1, test2, '*'));
     } else if (!testOperators(test1, test2, '/').equals("1")) {
     System.out.println("Division is broken! " + test1.toString() + "/" + test2.toString() + " appears to equal " + testOperators(test1, test2, '/'));
     } else {
     System.out.println("Everything is fine! All operators are working correctly!");
     }
     }
     }*/

}
