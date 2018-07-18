package common.datetime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

/**
 * A simple wrapper around a calendar for working with dates like 12/29/1977. Does not consider time.
 */
public class SimpleDate implements Serializable {

	private static final long serialVersionUID = 2285962420279644602L;

	private GregorianCalendar base;

	/**
	 * Create a new simple date.
	 * @param month the month
	 * @param day the day
	 * @param year the year
	 */
	public SimpleDate(int month, int day, int year) {
		init(new GregorianCalendar(year, month - 1, day));
	}

	SimpleDate(long time) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time);
		init(cal);
	}

	private SimpleDate() {
		init(new GregorianCalendar());
	}

	private void init(GregorianCalendar cal) {
		this.base = trimToDays(cal);
	}

	private GregorianCalendar trimToDays(GregorianCalendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * Returns this simple date as a <code>java.util.Date</code>
	 * @return this simple date as a Date
	 */
	public Date asDate() {
		return base.getTime();
	}

	/**
	 * Returns this date in milliseconds since 1970.
	 * @return
	 */
	public long inMilliseconds() {
		return asDate().getTime();
	}

	public int compareTo(Object date) {
		SimpleDate other = (SimpleDate) date;
		return asDate().compareTo(other.asDate());
	}

	public boolean equals(Object day) {
		if (!(day instanceof SimpleDate)) {
			return false;
		}
		SimpleDate other = (SimpleDate) day;
		return (base.equals(other.base));
	}

	public int hashCode() {
		return 29 * base.hashCode();
	}

	/**
	 * Returns todays date. A convenient static factory method.
	 */
	public static SimpleDate today() {
		return new SimpleDate();
	}

	/**
	 * Converts the specified date to a SimpleDate. Will trim hour, minute, second, and millisecond fields.
	 * @param date the java.util.Date
	 * @return the simple date
	 */
	public static SimpleDate valueOf(Date date) {
		return valueOf(date.getTime());
	}

	/**
	 * Converts the specified long time value to a SimpleDate. Will trim hour, minute, second, and millisecond fields.
	 * @param time time in millseconds since 1970
	 * @return the time as a SimpleDate
	 */
	public static SimpleDate valueOf(long time) {
		return new SimpleDate(time);
	}
	
	@Override
	public String toString() {
        return new SimpleDateFormat().format(base.getTime());
    }

}