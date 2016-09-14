package dnl.infra.cli;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Daniel Orr
 * 
 */
public class CLI {

	private CliParser cliParser;
	private Options options = new Options();
	private String applicationName = "";
	private List<DateFormat> dateFormats = new ArrayList<DateFormat>();
	
	/*
	 * Each string array represents a group of options of which only one may
	 * appear in the CLI input
	 */
	private List<String[]> prohibitedCoexistenceOptions = new ArrayList<String[]>();
	/*
	 * Each string array represents a group of options of which at least one
	 * option must appear in the CLI input
	 */
	private List<String[]> assertOneOfOptions = new ArrayList<String[]>();

	/*
	 * Each string array represents a group of 2 options of which the first
	 * option depends on the second, and therefore if the first is in the CLI
	 * input the other must be there as well.
	 */
	private List<String[]> optionDependencies = new ArrayList<String[]>();

	public CLI() {
		this(CliFlavorType.SIMPLE);
	}

	public CLI(CliFlavorType flavor) {
		cliParser = new CliParser(flavor);
		initDateFormats();
		addAction(new HelpAction());
	}

	public CLI(String applicationName) {
		this(CliFlavorType.SIMPLE);
		this.applicationName = applicationName;
	}

	private void initDateFormats() {
		dateFormats.add(new SimpleDateFormat("dd.MM.yyyy"));
		dateFormats.add(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
	}

	public void addDateFormat(DateFormat df) {
		dateFormats.add(df);
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void addFileSetting(String name, String description, boolean mandatory) {
		addSetting(name, "file", description, mandatory);
	}

	public void addDirSetting(String name, String description, boolean mandatory) {
		addSetting(name, "dir", description, mandatory);
	}

	public void addDateSetting(String name, String description, boolean mandatory) {
		addSetting(name, "date", description, mandatory);
	}

	public void addTimeRangeSetting(String name, String description, boolean mandatory) {
		addSetting(name, "time range", description, mandatory);
	}

	public void addIntSetting(String name, String description, boolean mandatory) {
		addSetting(name, "integer", description, mandatory);
	}

	public void addBooleanSetting(String name, String description, boolean mandatory) {
		addSetting(name, "boolean", description, mandatory);
	}

	public void addFlag(String name, String description) {
		CliFlag flag = new CliFlag(name, description);
		addFlag(flag);
	}

	public void addSetting(String name, String argType, String description) {
		CliSetting setting = new CliSetting(name, description);
		setting.setType(argType);
		options.registerOption(setting);
	}

	public void addSetting(String name, String argType, String description, boolean mandatory) {
		CliSetting setting = new CliSetting(name, description);
		setting.setType(argType);
		setting.setMandatory(mandatory);
		options.registerOption(setting);
	}

	private void addFlag(CliFlag flag) {
		options.registerOption(flag);
	}

	private void addAction(CliAction cliAction) {
		options.registerOption(cliAction);
	}

	public void addAction(String name, String description, CliActionListener actionListener) {
		addAction(name, null, description, actionListener);
	}

	public void addAction(String name, String type, String description, CliActionListener actionListener) {
		CliAction cliAction = new ListenerBasedCliAction(this, name, actionListener);
		if (actionListener instanceof AbstractCliAction) {
			AbstractCliAction aca = (AbstractCliAction) actionListener;
			aca.setCliAction(cliAction);
		}
		cliAction.setDescription(description);
		cliAction.setType(type);
		options.registerOption(cliAction);
	}

	public boolean isOptionSet(String optName) {
		assertCmd();
		CliOption cliOption = getOption(optName);
		if (cliOption == null) {
			return false;
		}
		return cliOption.isActive();
	}

	private CliOption getOption(String name) {
		CliOption opt = options.getOption(name);
		if (opt == null) {
			throw new IllegalArgumentException("No such option '" + name + "'");
		}
		return opt;
	}

	public String getString(String optName) {
		assertCmd();
		CliOption cliOption = getOption(optName);
		return cliOption.getValue();
	}

	public Date getDate(String optName) {
		assertCmd();
		String val = getString(optName);
		if (val == null) {
			return null;
		}
		Date date = parseDate(val);
		if (date != null) {
			return date;
		}
		System.err.println("The given date cannot be parsed: " + val);
		printUsageAndExit();
		return null;
	}

	public File getDir(String optName) {
		File d = getFile(optName);
		if (!d.isDirectory()) {
			System.err.println("The given arg is not a directory: " + d.getAbsolutePath());
			printUsageAndExit();
		}
		return d;
	}

	public File getFile(String optName) {
		assertCmd();
		String val = getString(optName);
		if (val == null) {
			return null;
		}
		File f = new File(val);
		return f;
	}

	public TimeRange getTimeRange(String optName) {
		assertCmd();
		String val = getString(optName);
		if (val == null) {
			return null;
		}
		int countMatches = StringUtils.countMatches(val, "-");
		if (countMatches != 1) {
			System.err.println("Time range separator is '-'. You entered: " + val);
			printUsageAndExit();
		}
		String[] splits = StringUtils.split(val, "-");
		Date d1 = parseDate(splits[0]);
		Date d2 = parseDate(splits[1]);
		if (d1 == null || d2 == null) {
			System.err.println("The given time range cannot be parsed: " + val);
			printUsageAndExit();
			return null;
		}
		return new TimeRange(d1, d2);
	}

	public Integer getInteger(String optName) {
		assertCmd();
		String val = getString(optName);
		if (val == null) {
			return null;
		}
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
		}
		System.err.println("The given string cannot be parsed into an integer: " + val);
		printUsageAndExit();
		throw new IllegalStateException();
	}

	public Double getDouble(String optName) {
		assertCmd();
		String val = getString(optName);
		if (val == null) {
			return null;
		}
		try {
			return Double.parseDouble(val);
		} catch (NumberFormatException e) {
		}
		System.err.println("The given string cannot be parsed into an integer: " + val);
		printUsageAndExit();
		throw new IllegalStateException();
	}

	public Boolean getBoolean(String optName) {
		assertCmd();
		String val = getString(optName);
		if (val == null) {
			return null;
		}
		if ("true".equals(val.toLowerCase())) {
			return Boolean.TRUE;
		}
		if ("false".equals(val.toLowerCase())) {
			return Boolean.FALSE;
		}
		System.err.println("The given string cannot be parsed into a boolean: " + val);
		printUsageAndExit();
		throw new IllegalStateException();
	}

	/**
	 * Prohibits the cli input to have the given options appear together.
	 * 
	 * @param options
	 */
	public void prohibitOptionCoexistence(String... options) {
		prohibitedCoexistenceOptions.add(options);
	}

	/**
	 * Assert that one of the given options exists in the cli input.
	 * 
	 * @param options
	 */
	public void assertOneOfOptionsExist(String... options) {
		assertOneOfOptions.add(options);
	}
	
	public void addOptionDependency(String opt1, String opt2){
		optionDependencies.add(new String[]{opt1, opt2});
	}

	public void parseCli(String[] args) {
		try {
			cliParser.parseCli(options, args);
		} catch (CliParseException pe) {
			System.err.println(pe.getMessage());
			printUsageAndExit();
			return;
		}
		handleProhibitedCoexistentOptions();
		handleAssertOneOfOptions();
		handleOptionDependencies();
	}

	private void handleProhibitedCoexistentOptions() {
		for (String[] optionGroup : prohibitedCoexistenceOptions) {
			int count = 0;
			for (String opt : optionGroup) {
				if (isOptionSet(opt)) {
					count++;
				}
			}
			if (count > 1) {
				printUsageAndExit("The options " + Arrays.toString(optionGroup) + " cannot coexist");
			}
		}
	}
	
	private void handleAssertOneOfOptions() {
		for (String[] optionGroup : assertOneOfOptions) {
			int count = 0;
			for (String opt : optionGroup) {
				if (isOptionSet(opt)) {
					count++;
				}
			}
			if (count == 0) {
				printUsageAndExit("At least on of the options " + Arrays.toString(optionGroup) + " must exist");
			}
		}
	}

	private void handleOptionDependencies() {
		for (String[] optionGroup : optionDependencies) {
			if(isOptionSet(optionGroup[0]) && !isOptionSet(optionGroup[1])){
				printUsageAndExit("Option '" + optionGroup[0] + "' dependes on '"+optionGroup[1]+"'");
			}
		}
	}

	private void assertCmd() {
		if (cliParser == null) {
			throw new IllegalStateException("call parseCli() first!");
		}
	}

	public boolean processActions() {
		List<CliAction> activeActions = cliParser.getActiveActions();
		for (CliAction cliAction : activeActions) {
			cliAction.executeAction();
		}

		if (activeActions.isEmpty()) {
			printUsageAndExit();
		}

		return !activeActions.isEmpty();
	}

	public void printUsageAndExit(String message) {
		System.err.println(message);
		printUsageAndExit();
	}

	public void printUsageAndExit() {
		// Use the inbuilt formatter class
		HelpFormatter formatter = new SimpleHelpFormatter(getApplicationName(), true);
		formatter.printHelp(options);
		System.exit(1);
	}

	private Date parseDate(String s) {
		for (DateFormat df : dateFormats) {
			try {
				Date date = df.parse(s);
				return date;
			} catch (java.text.ParseException e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @author Daniel Orr
	 * 
	 */
	private class HelpAction extends CliAction {

		private HelpAction() {
			super("help", "display this help and exit");
			setNumberOfArguments(NumberOfArguments.NONE);
		}

		@Override
		public void executeAction() {
			printUsageAndExit();
		}

	}
}
