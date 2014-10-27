package fraccalc.backEnd;

/**
 * A custom exception to handle things that go wrong
 *
 * @author precociouslyDigital
 *
 */
public class NotAFractionException extends Exception {

    /**
     * Generated Exception ID
     */
    private static final long serialVersionUID = 999175742470233017L;

    /**
     *reason for the exception
     */
    public String reason;

    /**
     * generates a NotAFractionException
     *
     * @param reason the reason for throwing the exception
     */
    public NotAFractionException(String reason) {
        this.reason = reason;
    }
}
