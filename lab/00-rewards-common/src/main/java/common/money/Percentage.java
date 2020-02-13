package common.money;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A percentage. Represented as a decimal value with scale 2 between 0.00 and 1.00.
 * 
 * A value object. Immutable.
 */
@Embeddable
public class Percentage implements Serializable {

	private static final long serialVersionUID = 8077279865855620752L;

	private BigDecimal value;

	/**
	 * Create a new percentage from the specified value. Value must be between 0 and 1. For example, value .45
	 * represents 45%. If the value has more than two digits past the decimal point it will be rounded up. For example,
	 * value .24555 rounds up to .25.
	 * @param the percentage value
	 * @throws IllegalArgumentException if the value is not between 0 and 1
	 */
	@JsonCreator
	public Percentage(BigDecimal value) {
		initValue(value);
	}

	/**
	 * Create a new percentage from the specified double value. Converts it to a BigDecimal with exact precision. Value
	 * must be between 0 and 1. For example, value .45 represents 45%. If the value has more than two digits past the
	 * decimal point it will be rounded up. For example, value .24555 rounds up to .25.
	 * @param the percentage value as a double
	 * @throws IllegalArgumentException if the value is not between 0 and 1
	 */
	public Percentage(double value) {
		initValue(BigDecimal.valueOf(value));
	}

	@SuppressWarnings("unused")
	private Percentage() {
	}

	private void initValue(BigDecimal value) {
		value = value.setScale(2, RoundingMode.HALF_UP);
		if (value.compareTo(BigDecimal.ZERO) == -1 || value.compareTo(BigDecimal.ONE) == 1) {
			throw new IllegalArgumentException("Percentage value must be between 0 and 1; your value was " + value);
		}
		this.value = value;
	}

	/**
	 * Convert the string representation of a percentage (e.g. 5% or 5) to a Percentage object.
	 * @param string the percentage string
	 * @return the percentage object
	 */
	public static Percentage valueOf(String string) {
		if (string == null || string.length() == 0) {
			throw new IllegalArgumentException("The percentage value is required");
		}
		boolean percent = string.endsWith("%");
		if (percent) {
			int index = string.lastIndexOf('%');
			string = string.substring(0, index);
		}
		BigDecimal value = new BigDecimal(string);
		if (percent) {
			value = value.divide(new BigDecimal(100));
		}
		return new Percentage(value);
	}

	/**
	 * Returns zero percent.
	 */
	public static Percentage zero() {
		return new Percentage(0);
	}

	/**
	 * Returns one hundred percent.
	 */
	public static Percentage oneHundred() {
		return new Percentage(1);
	}

	/**
	 * Add to this percentage.
	 * @param percentage the percentage to add
	 * @return the sum
	 * @throws IllegalArgumentException if the new percentage exceeds 1
	 */
	public Percentage add(Percentage percentage) throws IllegalArgumentException {
		return new Percentage(value.add(percentage.value));
	}

	/**
	 * Return this percentage as a double. Useful for when a double type is needed by an external API or system.
	 * @return this percentage as a double
	 */
	public double asDouble() {
		return value.doubleValue();
	}

	/**
	 * Return this percentage as a big decimal. Useful for when a big decimal type is needed by an external API or
	 * system.
	 * @return this percentage as a big decimal
	 */
	@JsonValue
	public BigDecimal asBigDecimal() {
		return value;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Percentage)) {
			return false;
		}
		return value.equals(((Percentage) o).value);
	}

	public int hashCode() {
		return value.hashCode();
	}

	public String toString() {
		return value.multiply(new BigDecimal("100")).setScale(0) + "%";
	}
}