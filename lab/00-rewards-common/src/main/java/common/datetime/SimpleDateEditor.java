package common.datetime;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * A formatter for Simple date properties. Converts object values to well-formatted strings and strings back to
 * values. Usable by a data binding framework for binding user input to the model.
 */
public class SimpleDateEditor extends PropertyEditorSupport {

	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
	
	@Override
	public String getAsText() {
		SimpleDate date = (SimpleDate) getValue();
		if (date == null) {
			return "";
		} else {
			return dateFormat.format(date.asDate());
		}
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(SimpleDate.valueOf(dateFormat.parse(text)));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unable to convert String '" + text + "' to a SimpleDate", e);
		}
	}
}