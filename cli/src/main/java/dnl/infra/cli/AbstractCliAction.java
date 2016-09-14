package dnl.infra.cli;

public abstract class AbstractCliAction implements CliActionListener {

	private CliAction cliAction;

	protected final void setCliAction(CliAction cliAction) {
		this.cliAction = cliAction;
	}

	public String getName() {
		return cliAction.getName();
	}

	public String getShortName() {
		return cliAction.getShortName();
	}

	public String getDescription() {
		return cliAction.getDescription();
	}

	public String getType() {
		return cliAction.getType();
	}

	public boolean isMandatory() {
		return cliAction.isMandatory();
	}

	public NumberOfArguments getNumberOfArguments() {
		return cliAction.getNumberOfArguments();
	}

	public boolean hasArg() {
		return cliAction.hasArg();
	}

	public String getCliValue() {
		return cliAction.getValue();
	}

	public String[] getValues() {
		return cliAction.getValues();
	}
	
}
