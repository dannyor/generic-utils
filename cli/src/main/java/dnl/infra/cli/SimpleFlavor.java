package dnl.infra.cli;

import org.apache.commons.lang.StringUtils;


/**
 * This flavor disregards the short name and uses the regular name with a single
 * dash. For example, if the option name is 'help' it will be invoced if '-help'
 * appears in the cli.
 * 
 * @author Daniel Orr
 * 
 */
public class SimpleFlavor implements CliFlavor {

	@Override
	public boolean isOption(String cliArgument) {
		if (cliArgument.startsWith("--"))
			return false;
		if (cliArgument.startsWith("-"))
			return true;
		return false;
	}

	@Override
	public String getOptionName(String cliArgument) {
		return StringUtils.removeStart(cliArgument, "-");
	}

	@Override
	public CliOption matchOption(String cliArgument, Options options) {
		String optionName = getOptionName(cliArgument);
		for (CliOption cliOption : options) {
			if (cliOption.getName().equals(optionName))
				return cliOption;
		}
		return null;
	}

}
