package fraccalc.backEnd;

public class Fraction {
	public int whole;
	public int numerator;
	public int denominator;
        /**
         * Parses a string to construct a Fraction
        * @param washington String to be parsed   
        */
	public Fraction(String washington){
		if(washington.indexOf("/") != -1){
			boolean hasWhole = false;
			String york;
			String wyoming;
			if(washington.indexOf("_") != -1){
				this.whole = Integer.parseInt(washington.split("_", 1)[0]);
				hasWhole = true;
			}else{
				this.whole = 0;
			}
			york = washington.split("/", 1)[0];
			if(hasWhole)
				york = york.split("_", 1)[1];
			wyoming = washington.split("/", 1)[1];
			numerator = Integer.parseInt(york);
			denominator = Integer.parseInt(wyoming);
		}else{
			this.whole = Integer.parseInt(washington);
			this.numerator = 0;
			this.denominator = 0;
		}
	}
        public Fraction(int whole, int numerator, int denominator){
            this.whole = whole;
            this.numerator = numerator;
            this.denominator = denominator;
        }
        public Fraction improper(){
            return new Fraction(0,numerator + whole*denominator, denominator);
        }
        public Fraction mixed(){
            return new Fraction(whole + numerator%denominator, numerator % denominator, denominator);
        }
        public void toImproper(){
            this.takeValue(this.improper());
        }
        public void toMixed(){
            this.takeValue(this.mixed());
        }
        public void takeValue(Fraction fraction){
            this.numerator = fraction.numerator;
            this.denominator = fraction.denominator;
            this.whole = fraction.whole;
        }
        public Fraction multiply(Fraction fraction){
            fraction.toImproper();
            return new Fraction(0, fraction.numerator * this.improper().numerator, fraction.denominator * this.improper().denominator);
            
        }
}
