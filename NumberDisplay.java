/**
 * The NumberDisplay class represents a digital number display that can hold
 * values from zero to a given limit. When incremented it rolls over to zero.
 *
 * @author Michael Kölling and David J. Barnes 
 * @version 2016.02.29
 */
public class NumberDisplay {
    private int limit;
    private int value;

    /**
     * Constructor for objects of class NumberDisplay.
     * Set the limit at which the display rolls over.
     */
    public NumberDisplay(int rollOverLimit) {
        limit = rollOverLimit;
        value = 0;
    }

    /** Return the current integer value. */
    public int getValue() {
        return value;
    }

    /**
     * Return the display value (the current value as a two-digit String).
     * If the value is less than ten, it will be padded with a leading zero.
     */
    public String getDisplayValue() {
        if (value < 10) {
            return "0" + value;
        } else {
            return "" + value;
        }
    }

    /**
     * Set the value of the display to the specified value.
     * If the new value is less than zero or >= limit, do nothing.
     */
    public void setValue(int replacementValue) {
        if ((replacementValue >= 0) && (replacementValue < limit)) {
            value = replacementValue;
        }
    }

    /** Increment the display value by one, rolling over to zero if the limit is reached. */
    public void increment() {
        value = (value + 1) % limit;
    }
}
