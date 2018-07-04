package common.money;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

/**
 * A formatter for Monetary amount properties. Converts object values to well-formatted strings and strings back to
 * values. Usable by a data binding framework for binding user input to the model.
 */
public class MonetaryAmountEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		MonetaryAmount amount = (MonetaryAmount) getValue();
		if (amount == null) {
			return "";
		} else {
			return amount.toString();
		}
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			setValue(MonetaryAmount.valueOf(text));
		} else {
			setValue(null);
		}
	}


}
