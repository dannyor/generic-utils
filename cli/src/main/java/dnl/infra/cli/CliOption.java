package dnl.infra.cli;

import java.util.ArrayList;
import java.util.List;

abstract class CliOption {
	private String name;
	private String shortName;
	private String description;
	private NumberOfArguments expectedNumberOfArguments = NumberOfArguments.NONE;

	private List<String> values;
	protected boolean isActive;
	protected boolean mandatory;

	public CliOption(String name) {
		this.name = name;
	}

	public CliOption(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public CliOption(String name, NumberOfArguments numberOfArguments) {
		this.name = name;
		setNumberOfArguments(numberOfArguments);
	}

	public CliOption(String name, String description, NumberOfArguments numberOfArguments) {
		this.name = name;
		this.description = description;
		setNumberOfArguments(numberOfArguments);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public NumberOfArguments getNumberOfArguments() {
		return expectedNumberOfArguments;
	}

	public void setNumberOfArguments(NumberOfArguments numberOfArguments) {
		this.expectedNumberOfArguments = numberOfArguments;
		if (hasArg()) {
			this.values = new ArrayList<String>();
		}
	}

	/**
	 * 
	 * @return <code>true</code> if this <code>CliOption</code> was present in
	 *         the CLI.
	 */
	protected boolean isActive() {
		return isActive;
	}

	protected void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean hasArg() {
		return !NumberOfArguments.NONE.equals(expectedNumberOfArguments);
	}

	protected String addValue(String value) {
		if (!hasArg()) {
			return "This option has no args";
		}
		if (NumberOfArguments.UNLIMITED.equals(expectedNumberOfArguments)) {
		} else if (values.size() >= getNumberOfArguments().ordinal()) {
			return "Too many args for option '" + name + "'";
		}

		if (value.startsWith("-")) {
			return "Given value is an option!";
		}

		values.add(value);
		return null;
	}

	/**
	 * Assumes there is only one value
	 * 
	 * @return
	 */
	public String getValue() {
		if (values == null || values.isEmpty())
			return null;
		return values.get(0);
	}

	public String[] getValues() {
		if (values == null || values.isEmpty())
			return new String[] {};
		return values.toArray(new String[values.size()]);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [name=" + name + ", expectedNumberOfArguments="
				+ expectedNumberOfArguments + ", values=" + values + ", mandatory=" + mandatory
				+ "]";
	}

}
