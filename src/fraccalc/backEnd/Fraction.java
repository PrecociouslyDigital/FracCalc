package fraccalc.backEnd;

public class Fraction {
	public int whole;
	public int numerator;
	public int denominator;
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
}
