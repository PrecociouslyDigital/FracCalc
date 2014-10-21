package fraccalc.backEnd;
public class NotAFractionException extends Exception {
	/**
	 * Generated Exception ID
	 */
	private static final long serialVersionUID = 999175742470233017L;
        public String reason;
	public NotAFractionException(String rea){this.reason = rea;}
}
