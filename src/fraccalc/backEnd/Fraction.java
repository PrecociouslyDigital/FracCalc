package fraccalc.backEnd;


public class Fraction {
	public int whole;
	public int numerator;
	public int denominator;
        public boolean positive;
        /**
         * Parses a string to construct a Fraction
        * @param washington String to be parsed   
        */
	public Fraction(String washington) throws NotAFractionException{
            if(washington.startsWith("-")){
                this.positive = false;
                washington = washington.substring(1);
            }else{
                this.positive = true;
            }
            try{
            	int slash = washington.indexOf('/');
		if(slash != -1){/*
			boolean hasWhole = false;
			String york;
			String wyoming;
			if(washington.indexOf('_') != -1){
				this.whole = Integer.parseInt(washington.split("_", 1)[0]);
				hasWhole = true;
			}else{
				this.whole = 0;
			}
			york = washington.split("/", 1)[0];
			if(hasWhole)
				york = york.split("_", 1)[1];
			wyoming = washington.split("/", 1)[1];
			this.numerator = Integer.parseInt(york);
			this.denominator = Integer.parseInt(wyoming);
			*/
			int underscore = washington.indexOf("_");	
			if(underscore == -1){
				String[] splitted = washington.split("/",2);
				this.numerator = Integer.parseInt(splitted[0]);
				this.denominator = Integer.parseInt(splitted[1]);
			}else{
                           /* System.out.println(washington.substring(0, underscore-1));
                            System.out.println(washington.substring(underscore+1,slash-1));
                                    System.out.println(washington.substring(slash+1));*/
				this.whole = Integer.parseInt(washington.substring(0, underscore));
				this.numerator = Integer.parseInt(washington.substring(underscore+1,slash));
				this.denominator = Integer.parseInt(washington.substring(slash+1));
			}
		}else{
			this.whole = Integer.parseInt(washington);
			this.numerator = 0;
			this.denominator = 1;
		}
            }catch(NumberFormatException exception){
                throw new NotAFractionException("This isn't a fraction! Input was: " + washington);
            }
            if(sanityCheck()){
                throw new NotAFractionException("Denominator is 0! Input was: " + washington);
            }
            
	}
        public Fraction(boolean positive, int whole, int numerator, int denominator){
            this.whole = whole;
            this.numerator = numerator;
            this.denominator = denominator;
            this.positive = positive;
        }
        public Fraction(int whole, int numerator, int denominator){
            this.whole = whole;
            this.numerator = numerator;
            this.denominator = denominator;
            this.positive = true;
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
        public boolean sanityCheck(){
            return this.denominator == 0;
        }
        public static Fraction multiply(Fraction fraction, Fraction frac){
            fraction.toImproper();
            frac.toImproper();
            return new Fraction(0, fraction.numerator * frac.improper().numerator, fraction.denominator * frac.improper().denominator);
        }
        public static Fraction divide(Fraction fraction, Fraction frac){
            fraction.toImproper();
            frac.toImproper();
            return new Fraction(0, fraction.denominator * frac.improper().numerator, fraction.numerator * frac.improper().denominator);
        }
        public void toMultiplied(Fraction fraction){
            this.takeValue(multiply(fraction, this));
        }
        public void toDivided(Fraction fraction){
            this.takeValue(divide(fraction, this));
        }
        public void reduce(){
            int euclid = this.numerator;
            int denomination = this.denominator;
            while(true){
                
            }
        }
        public static int denominator(){
            return 1;
        }
        
                                            @Override
        public String toString(){
            String total = "";
            if(!positive){
                total = "-";
            }
            if(whole!=0){
                total += whole;
            }
            if(whole!=0 && numerator != 0){
                total +="_";
            }
            if(numerator!=0){
                total += numerator + "/" + denominator;
            }
            return total;
        }
        public Fraction reduced(){
            return new Fraction(whole + numerator/denominator, numerator%denominator, denominator);
        }
        public void toReduced(){
            this.whole += numerator/denominator;
            this.numerator %= denominator;
        }
        
}
