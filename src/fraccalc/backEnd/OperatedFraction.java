package fraccalc.backEnd;

public class OperatedFraction {
	public Fraction fraction;
	public Operator operator;

    /**
     * Constructor for a fraction and an operator.
     * @param input String to be parsed
     * @throws fraccalc.backEnd.NotAFractionException
     */
    public OperatedFraction(String input) throws NotAFractionException{
            switch(input.charAt(0)){
                case '+':
                    this.operator = Operator.plus;
                    this.fraction = new Fraction(input.substring(1));
                    break;
                case '-':
                   this.operator = Operator.minus;
                   this.fraction = new Fraction(input.substring(1));
                   break;
                case '/':
                   this.operator = Operator.divide;
                   this.fraction = new Fraction(input.substring(1));
                   break;
                case '*':
                   this.operator = Operator.times;
                   this.fraction = new Fraction(input.substring(1));
                   break;
                default:
                    this.operator = Operator.none;
                    this.fraction = new Fraction(input);
            }
            
        }
	public OperatedFraction(Fraction frank, Operator opera){
		this.fraction = frank;
		this.operator = opera;
	}
        public OperatedFraction(Fraction frank){
            this.fraction = frank;
            this.operator = Operator.none;
        }
        public Fraction evaulate(Fraction panda){
            switch (operator){
                case plus:
                    
                    
            }
        }
}
