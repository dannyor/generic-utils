package dnl.infra.cli;


interface CliFlavor {

	public boolean isOption(String cliArgument);
	public String getOptionName(String cliArgument);
	public CliOption matchOption(String cliArgument, Options options);
}
