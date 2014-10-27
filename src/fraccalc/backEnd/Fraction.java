package fraccalc.backEnd;

/**
 *
 * @author bendpalias
 */
public class Fraction {

    /**
     * Whole part of fraction
     */
    public int whole;

    /**
     * Numerator of fraction
     */
    public int numerator;

    /**
     * Denominator of Fraction
     */
    public int denominator;

    /**
     * Positivity of Fraction
     */
    public boolean positive;

    /**
     * Parses a string to construct a Fraction
     *
     * @param input String to be parsed
     * @throws fraccalc.backEnd.NotAFractionException
     */
    public Fraction(String input) throws NotAFractionException {
        if (input.startsWith("-")) {
            this.positive = false;
            input = input.substring(1);
        } else {
            this.positive = true;
        }
        try {
            int slash = input.indexOf('/');
            if (slash != -1) {
                int underscore = input.indexOf("_");
                if (underscore == -1) {
                    String[] splitted = input.split("/", 2);
                    this.numerator = Integer.parseInt(splitted[0]);
                    this.denominator = Integer.parseInt(splitted[1]);
                } else {
                    this.whole = Integer.parseInt(input.substring(0, underscore));
                    this.numerator = Integer.parseInt(input.substring(underscore + 1, slash));
                    this.denominator = Integer.parseInt(input.substring(slash + 1));
                }
            } else {
                this.whole = Integer.parseInt(input);
                this.numerator = 0;
                this.denominator = 1;
            }
        } catch (NumberFormatException exception) {
            throw new NotAFractionException("This isn't a fraction!" /*+ "Offender was: " + input*/);
        }
        if (sanityCheck()) {
            throw new NotAFractionException("Denominator is 0!" /*+  "Input was: " + input*/);
        }

    }

    /**
     * Fraction constructor
     *
     * @param positive is it positive?
     * @param whole whole part of fraction
     * @param numerator numerator of fraction
     * @param denominator denominator of fraction
     */
    public Fraction(boolean positive, int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
        this.positive = positive;
        if (sanityCheck()) {
            //System.out.println("Denominator is zero! Value set to whole part.");
            this.numerator = 0;
            this.denominator = 1;
        }
        this.reduce();
    }

    /**
     * Fraction constructor, assuming the fraction is positive
     *
     * @param whole whole part of fraction
     * @param numerator numerator of fraction
     * @param denominator denominator of fraction
     */
    public Fraction(int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
        this.positive = true;
        if (sanityCheck()) {
            //System.out.println("Denominator is zero! Value set to whole part.");
            this.numerator = 0;
            this.denominator = 1;
        }
        this.reduce();
    }

    /**
     * returns the improper form of a fraction
     *
     * @return improper form of fraction
     */
    public Fraction improper() {
        return new Fraction(0, numerator + whole * denominator, denominator);
    }

    /**
     * returns the mixed number form of a fraction
     *
     * @return mixed number form of fraction
     */
    public Fraction mixed() {
        return new Fraction(whole + numerator / denominator, numerator % denominator, denominator);
    }

    /**
     * turns the fraction into an improper fraction
     */
    public void toImproper() {
        this.takeValue(this.improper());
    }

    /**
     * turns the fraction into a mixed number
     */
    public void toMixed() {
        this.takeValue(this.mixed());
    }

    /**
     * turns the fraction into an mixed number fraction
     */
    /**
     * makes a fraction take the value of another fraction
     *
     * @param fraction fraction whose value should be taken
     */
    public void takeValue(Fraction fraction) {
        this.numerator = fraction.numerator;
        this.denominator = fraction.denominator;
        this.whole = fraction.whole;
    }

    /**
     * makes sure the denominator isn't zero
     *
     * @return whether the denominator is zero
     */
    public boolean sanityCheck() {
        return this.denominator == 0;
    }

    /**
     * correctly formats the fraction for output.
     */
    public void format() {
        this.toMixed();
        this.reduce();
    }

    /**
     * multiplies two fractions
     *
     * @param fraction fraction to be multiplied
     * @param frac fraction to be multiplied
     * @return multiplied value
     */
    public static Fraction multiply(Fraction fraction, Fraction frac) {
        fraction.toImproper();
        frac.toImproper();
        return new Fraction(!fraction.positive ^ frac.positive,
                0,
                fraction.numerator * frac.numerator,
                fraction.denominator * frac.denominator
        );
    }

    /**
     * divides two fractions
     *
     * @param operator1 fraction to be divided
     * @param operator2 fraction that will be the divisor
     * @return divided value
     */
    public static Fraction divide(Fraction operator1, Fraction operator2) throws NotAFractionException {
        if (operator2.numerator == 0 && operator2.whole == 0) {
            throw new NotAFractionException("Dividing by zero!");
        } else {
            operator1.toImproper();
            operator2.toImproper();
            return new Fraction(!operator1.positive ^ operator2.positive,
                    0,
                    operator1.numerator * operator2.denominator,
                    operator1.denominator * operator2.numerator
            );
        }
    }

    /**
     * adds two fractions together
     *
     * @param fraction fraction to be added
     * @param frac fraction to be added
     * @return added value
     */
    public static Fraction add(Fraction fraction, Fraction frac) {
        fraction.reduce();
        frac.reduce();
        fraction.toImproper();
        frac.toImproper();
        fraction.applySign();
        frac.applySign();
        int commonMultiple = lcm(fraction.denominator, frac.denominator);
        fraction.takeDenomValue(commonMultiple);
        frac.takeDenomValue(commonMultiple);
        Fraction output = new Fraction(fraction.whole + frac.whole,
                fraction.numerator + frac.numerator,
                commonMultiple);
        //output.format();
        return output;
    }

    /**
     * subtracts two fractions
     *
     * @param fraction fraction to be subtracted
     * @param frac fraction to be from the first
     * @return subtracted value
     */
    public static Fraction subtract(Fraction fraction, Fraction frac) {
        fraction.reduce();
        frac.reduce();
        fraction.toImproper();
        frac.toImproper();
        fraction.applySign();
        frac.applySign();
        int commonMultiple = lcm(fraction.denominator, frac.denominator);
        fraction.takeDenomValue(commonMultiple);
        frac.takeDenomValue(commonMultiple);
        return new Fraction(fraction.whole - frac.whole,
                fraction.numerator - frac.numerator,
                commonMultiple);
    }

    /**
     * reduces a fraction's numerator and denominator
     */
    public void reduce() {
        handlePositivity();
        formatReduce();
    }

    public void formatReduce() {
        int reducingFactor = gcd(this.numerator, this.denominator);
        this.numerator /= reducingFactor;
        this.denominator /= reducingFactor;
    }

    public void handlePositivity() {
        if (this.whole < 0) {
            this.whole = -this.whole;
            this.positive = !this.positive;
        }
        if (this.numerator < 0) {
            this.numerator = -this.numerator;
            this.positive = !this.positive;
        }
        if (this.denominator < 0) {
            this.denominator = -this.denominator;
            this.positive = !this.positive;
        }
    }

    /**
     * scales a fractions denominator and numerator so that the denominator is a
     * certain value. if value is not a multiple of the current denominator, if
     * will default to the largest multiple of the current denominator lesser
     * than value
     *
     * @param value value for the denominator to be
     */
    private void takeDenomValue(int value) {
        numerator *= value / denominator;
        denominator = value;
    }

    /**
     * applies a fraction's sign to its numbers
     */
    public void applySign() {
        if (!this.positive) {
            this.whole = -this.whole;
            this.numerator = -this.numerator;
        }
        this.positive = true;
    }

    /**
     * creates the string representation of the fraction
     *
     * @return the string representation of the fraction.
     */
    @Override
    public String toString() {
        this.format();
        String total = "";
        if (!positive) {
            total += "-";
        }
        if (whole != 0) {
            total += whole;
        }
        if (whole != 0 && numerator != 0) {
            total += "_";
        }
        if (numerator != 0) {
            total += numerator + "/" + denominator;
        }
        if (whole == 0 && numerator == 0) {
            total = "0";
        }
        return total;
    }

    private static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        } else {
            return gcd(q, p % q);
        }
    }

    private static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }
}
