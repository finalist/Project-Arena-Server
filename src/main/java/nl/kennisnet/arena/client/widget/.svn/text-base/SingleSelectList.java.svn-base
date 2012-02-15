package nl.kennisnet.arena.client.widget;

public class SingleSelectList extends SelectList {

	public SingleSelectList() {
		super();
	}

	@Override
	protected void clickOption(Object id) {
		setValue(id, true);
	}

	public void setActive(Object id) {
		clickOption(id);
	}

	@Override
	public Object getValue() {
		for (Option option : getOptions()) {
			if (option.isSelected()) {
				return option.getId();
			}
		}
		return null;

	}

	@Override
	public void setValue(Object value) {
		for (Option option : getOptions()) {
			if (option.getId().equals(value)) {
				option.select();
			} else {
				option.deselect();
			}
		}
	}

}
