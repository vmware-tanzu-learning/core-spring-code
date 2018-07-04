package common.datetime;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleDateEditorTests {

	private SimpleDateEditor editor = new SimpleDateEditor();

	@Test
	public void testGetAsText() {
		SimpleDate date = new SimpleDate(12, 29, 1977);
		editor.setValue(date);
		assertEquals("December 29, 1977", editor.getAsText());
	}

	@Test
	public void testSetAsText() {
		editor.setAsText("December 29, 1977");
		SimpleDate date = (SimpleDate) editor.getValue();
		assertEquals(new SimpleDate(12, 29, 1977), date);
	}

	@Test
	public void testSetAsTextBogus() {
		try {
			editor.setAsText("December 29th, 1977");
			fail("Should have failed - illegal argument");
		}
		catch (IllegalArgumentException e) {

		}
	}
}
