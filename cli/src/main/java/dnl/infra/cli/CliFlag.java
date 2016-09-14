package dnl.infra.cli;

class CliFlag extends CliOption{

	public CliFlag(String name) {
		super(name, NumberOfArguments.NONE);
	}

	public CliFlag(String name, NumberOfArguments numberOfArguments) {
		super(name, numberOfArguments);
	}

	public CliFlag(String name, String description) {
		super(name, description);
	}

	public boolean isFlagSet(){
		return isActive();
	}

	@Override
	public void setMandatory(boolean mandatory) {
		throw new UnsupportedOperationException("Only A CliSetting may be mandatory.");
	}
	
	
}
