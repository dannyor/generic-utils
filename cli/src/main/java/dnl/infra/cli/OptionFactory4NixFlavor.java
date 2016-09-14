package dnl.infra.cli;

/**
 * 
 * @author Daniel Orr
 * 
 */
public class OptionFactory4NixFlavor {

	public static CliSetting createSetting(String name) {
		return new CliSetting(name);
	}

	public static CliSetting createMandatorySetting(String name) {
		CliSetting setting = new CliSetting(name);
		setting.setMandatory(true);
		return setting;
	}

	public static CliSetting createMandatorySetting(String name, String shortName) {
		CliSetting setting = new CliSetting(name);
		setting.setShortName(shortName);
		setting.setMandatory(true);
		return setting;
	}

	public static CliSetting createMandatorySetting(String name, String shortName,
			String description) {
		CliSetting setting = new CliSetting(name, description);
		setting.setShortName(shortName);
		setting.setMandatory(true);
		return setting;
	}

	public static CliSetting createSetting(String name, String shortName,
			String description) {
		CliSetting setting = new CliSetting(name, description);
		setting.setShortName(shortName);
		return setting;
	}

	public static CliFlag createFlag(String name, String shortName, String description) {
		CliFlag flag = new CliFlag(name, description);
		flag.setShortName(shortName);
		return flag;
	}

	public static CliFlag createFlag(String name, String description) {
		CliFlag flag = new CliFlag(name, description);
		return flag;
	}
}
