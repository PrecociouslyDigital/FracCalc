package fraccalc.backEnd;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for expression parsing.
 *
 * @author bendpalias
 */
public class Parser {

    /**
     * Parses then evaluates the string given, then returns it as a fraction
     * <p>
     * The method puts together the processParentheses and evaluate methods and
     * handles some problems they have.
     *
     * @param stringIn The string to be parsed
     * @return The fraction that is the evaluated expression()
     * @see processParentheses()
     * @see  evaluate
     */
    public static Fraction parse(String stringIn) {
        stringIn = stringIn.trim();
        Fraction output = null;
        try {
            if (stringIn.indexOf('(') != -1) {
                stringIn = processParentheses(stringIn);
            }
            output = evaluate(stringIn);

        } catch (NotAFractionException ex) {
            System.out.println(ex);
            return null;
        }
        return output;
    }

    /**
     * Method that processes the parentheses in an expression
     *
     * @param stringIn
     * @return Expression with the parentheses evaluated
     * @throws NotAFractionException
     */
    public static String processParentheses(String stringIn) throws NotAFractionException {
        //input = input.replaceAll("\\s+","");
        int openParentheses = stringIn.indexOf("(");
        int closeParentheses = -1;
        String stored;
        //recursively getting rid of what's in parenthathes
        while (openParentheses != -1) {
            boolean found = false;
            int parenthesesCount = 1;
            for (int i = openParentheses + 1; i < stringIn.length(); i++) {
                switch (stringIn.charAt(i)) {
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
                stored += stringIn.substring(0, openParentheses);
            }
            stored += parse(stringIn.substring(openParentheses + 1, closeParentheses)).toString();
            if (stored.isEmpty()) {
                break;
            }
            if (closeParentheses < stringIn.length()) {
                stored += stringIn.substring(closeParentheses + 1);
            }
            stringIn = stored;
            openParentheses = stringIn.indexOf("(");
        }

        return stringIn;
    }

    /**
     * Evaluates an expression without parentheses
     *
     * @param stringIn
     * @return evaluated Fraction.
     */
    public static Fraction evaluate(String stringIn) {
        ArrayList<String> expressions = new ArrayList<String>(Arrays.asList(stringIn.split(" ")));
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
                stringIn = expressions.get(0);
            }

        } catch (NotAFractionException ex) {
            System.out.println(ex.reason);
            return null;
        }
        try {
            Fraction output = new Fraction(stringIn);
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
    public static void test() {
        //Some basic tests, to make sure fractions are being parsed correctly
        testAgainst("1", "1");
        testAgainst("0", "0");
        //Making sure basic operators are working
        testAgainst("1 + 2", "3");
        testAgainst("1_2/3 + 1_2/3", "3_1/3");
        testAgainst("16/3 / 4/9", "12");
        //Making sure order of operations works
        testAgainst("1 + 2 * 2", "5");
        testAgainst("1/2 + 1/4 * 2", "1");
        //Making sure parentheses work
        testAgainst("2 * (1 - 3)", "-4");
        testAgainst("2_2/3 * (1/2 + 1_1/2)", "5_1/3");
        //Making sure nested parentheses work
        testAgainst("((1 + 3) * (1 + 2)) * 2", "24");
        testAgainst("((1_1/2 + 3_1/2) + (2_2/3 + 1_1/3)) * (2_2/3 - 2)", "6");
        //Making sure negitives work
        testAgainst("-2 * 4", "-8");
        testAgainst("(-1_2/3 + 2) * -5/4", "-5/12");
        //Making sure you can't divide by zero.
        testAgainst("5 / 0", "Denominator is 0!");
        testAgainst("1_2/0", "Denominator is 0!");
        //Making sure invalid operations don't break the parser
        testAgainst("1 + + 1/2", "This isn't a valid expression");
        testAgainst("Hello world!", "This isn't a valid expression");
        testAgainst("melon", "This isn't a fraction!");
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
        System.out.println("Trying " + expression);
        Fraction output = parse(expression);
        if (output != null) {
            if (!expected.equals(output.toString()))  {
                System.out.println("Evaluating " + expression + " failed! Returned " + parse(expression).toString());
            }else {
            System.out.println(expression + " evaluated correctly");
        }
        }else{
            System.out.println(expression + " returned null. Should do so for exceptions.");
        }
    }
}
