package fraccalc.backEnd;

public class Fraction {

    public int whole;
    public int numerator;
    public int denominator;
    public boolean positive;
    public static final Fraction zero = new Fraction(0, 0, 1);

    /**
     * Parses a string to construct a Fraction
     *
     * @param input String to be parsed
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
            throw new NotAFractionException("This isn't a fraction! Input was: " + input);
        }
        if (sanityCheck()) {
            throw new NotAFractionException("Denominator is 0! Input was: " + input);
        }

    }

    public Fraction(boolean positive, int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
        this.positive = positive;
        if (sanityCheck()) {
            System.out.println("Denominator is zero! Value set to zero.");
            this.takeValue(zero);
        }
    }

    public Fraction(int whole, int numerator, int denominator) {
        this.whole = whole;
        this.numerator = numerator;
        this.denominator = denominator;
        this.positive = true;
        if (sanityCheck()) {
            System.out.println("Denominator is zero! Value set to zero.");
            this.takeValue(zero);
        }

    }

    public Fraction improper() {
        return new Fraction(0, numerator + whole * denominator, denominator);
    }

    public Fraction mixed() {
        return new Fraction(whole + numerator / denominator, numerator % denominator, denominator);
    }

    public void toImproper() {
        this.takeValue(this.improper());
    }

    public void toMixed() {
        this.takeValue(this.mixed());
    }

    public void takeValue(Fraction fraction) {
        this.numerator = fraction.numerator;
        this.denominator = fraction.denominator;
        this.whole = fraction.whole;
    }

    public boolean sanityCheck() {
        return this.denominator == 0;
    }

    public void format() {
        this.toMixed();
        this.reduce();
    }

    public static Fraction multiply(Fraction fraction, Fraction frac) {
        fraction.toImproper();
        frac.toImproper();
        return new Fraction(0, fraction.numerator * frac.improper().numerator, fraction.denominator * frac.improper().denominator);
    }

    public static Fraction divide(Fraction fraction, Fraction frac) {
        fraction.toImproper();
        frac.toImproper();
        return new Fraction(0, fraction.denominator * frac.improper().numerator, fraction.numerator * frac.improper().denominator);
    }

    public void toMultiplied(Fraction fraction) {
        this.takeValue(multiply(fraction, this));
    }

    public void toDivided(Fraction fraction) {
        this.takeValue(divide(fraction, this));
    }

    public static Fraction add(Fraction fraction, Fraction frac) {
        fraction.reduce();
        frac.reduce();
        fraction.applySign();
        frac.applySign();
        int commonMultiple = lcm(fraction.denominator, frac.denominator);
        fraction.takeDenomValue(commonMultiple);
        frac.takeDenomValue(commonMultiple);
        return new Fraction(fraction.whole + frac.whole, fraction.numerator + frac.numerator, commonMultiple);
    }

    public static Fraction subtract(Fraction fraction, Fraction frac) {
        fraction.reduce();
        frac.reduce();
        fraction.applySign();
        frac.applySign();
        int commonMultiple = lcm(fraction.denominator, frac.denominator);
        fraction.takeDenomValue(commonMultiple);
        frac.takeDenomValue(commonMultiple);
        return new Fraction(fraction.whole - frac.whole, fraction.numerator - frac.numerator, commonMultiple);
    }

    public void reduce() {
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
        int reducingFactor = gcd(this.numerator, this.denominator);
        this.numerator /= reducingFactor;
        this.denominator /= reducingFactor;
    }

    private void takeDenomValue(int value) {
        numerator *= value / denominator;
        denominator = value;
    }

    public void applySign() {
        if (!this.positive) {
            this.whole = -this.whole;
            this.numerator = -this.numerator;
        }
        this.positive = true;
    }

    @Override
    public String toString() {
        String total = "";
        if (!positive) {
            total = "-";
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
