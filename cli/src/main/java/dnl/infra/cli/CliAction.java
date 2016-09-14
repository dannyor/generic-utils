package dnl.infra.cli;

/**
 * A <code>CliAction</code> is performed after all settings has been read.
 * 
 * @author Daniel Orr
 * 
 */
abstract class CliAction extends CliSetting {

	/*
	 * Occasionaly actions access some other settings or flags.
	 */
	private Options options;
	
	public CliAction(String name) {
		super(name);
		this.setNumberOfArguments(NumberOfArguments.UNLIMITED);
	}

	public CliAction(String name, String description) {
		super(name, description);
		this.setNumberOfArguments(NumberOfArguments.UNLIMITED);
	}

	public CliAction(String name, String type, String description) {
		super(name, description);
		setType(type);
		this.setNumberOfArguments(NumberOfArguments.UNLIMITED);
	}

	@Override
	public void setMandatory(boolean mandatory) {
		throw new UnsupportedOperationException("Only A CliSetting may be mandatory.");
	}
	
	protected CliFlag accessCliFlag(String flagName){
		return (CliFlag) options.getOption(flagName);
	}

	protected CliSetting accessCliSetting(String settingName){
		return (CliSetting) options.getOption(settingName);
	}
	
	protected void injectOptions(Options options){
		this.options = options;
	}
	
	public abstract void executeAction();
}
