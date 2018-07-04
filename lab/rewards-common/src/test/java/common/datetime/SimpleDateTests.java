package common.datetime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Unit tests for the "Simple Date" wrapper around a Calendar that tracks
 * month/date/year only, with no provision for tracking time.
 */
public class SimpleDateTests {

	@Test
	public void testToday() {
		SimpleDate today = SimpleDate.today();
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		assertEquals(today.asDate(), cal.getTime());
	}

	@Test
	public void testValueOfDate() {
		SimpleDate today = SimpleDate.today();
		Date date = today.asDate();
		SimpleDate today2 = SimpleDate.valueOf(date);
		assertEquals(today, today2);
	}

	@Test
	public void testValueOfTime() {
		SimpleDate today = SimpleDate.today();
		long time = today.inMilliseconds();
		SimpleDate today2 = SimpleDate.valueOf(time);
		assertEquals(today, today2);
	}
}
