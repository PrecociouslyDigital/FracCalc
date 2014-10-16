package fraccalc.backEnd;

public class OperatedFraction {
	public Fraction fraction;
	public Operator operator;
        public OperatedFraction(String sandra){
            switch(sandra.charAt(0)){
                case '+':
                    this.operator = Operator.plus;
                    this.fraction = new Fraction(sandra.substring(1));
                    break;
                case '-':
                   this.operator = Operator.minus;
                   this.fraction = new Fraction(sandra.substring(1));
                   break;
                case '/':
                   this.operator = Operator.divide;
                   this.fraction = new Fraction(sandra.substring(1));
                   break;
                case '*':
                   this.operator = Operator.times;
                   this.fraction = new Fraction(sandra.substring(1));
                   break;
                default:
                    this.operator = Operator.none;
                    this.fraction = new Fraction(sandra);
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
