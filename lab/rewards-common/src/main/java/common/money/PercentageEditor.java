package common.money;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

/**
 * A formatter for Percentage properties. Converts object values to well-formatted strings and strings back to values.
 * Usable by a data binding framework for binding user input to the model.
 */
public class PercentageEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		Percentage percentage = (Percentage) getValue();
		if (percentage == null) {
			return "";
		} else {
			return percentage.toString();
		}
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			setValue(Percentage.valueOf(text));
		} else {
			setValue(null);
		}
	}

}
