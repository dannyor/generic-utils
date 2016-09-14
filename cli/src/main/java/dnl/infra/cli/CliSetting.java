package dnl.infra.cli;

/**
 * 
 * @author Daniel Orr
 * 
 */
class CliSetting extends CliOption {

	private String type;

	/**
	 * Creates a new <code>CliSetting</code> with the default of one argument.
	 * 
	 * @param name
	 *            the option name for this setting
	 */
	public CliSetting(String name) {
		super(name, NumberOfArguments.ONE);
	}

	/**
	 * Creates a new <code>CliSetting</code> with the default of one argument.
	 * 
	 * @param name
	 *            the option name for this setting
	 * @param description
	 */
	public CliSetting(String name, String description) {
		super(name, description, NumberOfArguments.ONE);
	}

	/**
	 * Creates a new <code>CliSetting</code>.
	 * 
	 * @param name
	 *            the option name for this setting
	 * @param description
	 * @param numberOfArguments
	 */
	public CliSetting(String name, String description, NumberOfArguments numberOfArguments) {
		super(name, description, numberOfArguments);
		if (NumberOfArguments.NONE.equals(numberOfArguments)) {
			throw new IllegalArgumentException(
					"A setting must have at least one argument. Otherwise use a flag.");
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
