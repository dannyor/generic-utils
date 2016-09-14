package dnl.infra.cli;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * This parser supports 3 option types: <li>Setting: Sets a value (or multiple
 * values) <li>Action: Results in an invocation of an action. May have
 * paramters. <li>Flag: Has no arguments. Just notes the existence of a
 * condition.
 * 
 * @author Daniel Orr
 * 
 */
class CliParser {

	private List<CliAction> activeActions;
	private Options options;
	private CliFlavor cliFlavor = new SimpleFlavor(); 
	
	public CliParser(CliFlavorType flavor) {
		this.cliFlavor = FlavorFactory.getFlavor(flavor);
	}

	public void parseCli(Options options, String[] args) throws CliParseException {
		this.options = options;
		CliOption opt = null;
		for (int i = 0; i < args.length; i++) {
			if (cliFlavor.isOption(args[i])) {
				opt = getOption(args[i]);
				if (opt == null) {
					failAndExit("No such option: " + args[i]);
					return;
				}
			} else {
				if (opt == null) {
					failAndExit("arguments are malformed.");
				}
				String addValueProblem = opt.addValue(args[i]);
				if(addValueProblem != null){
					failAndExit(addValueProblem);
				}
			}
		}

		checkOptionsIntegrity();
		registerActions();
		
		if(!options.getOption("help").isActive()){
			checkMandatoryOptions();
		}
	}
	
	/**
	 * Checks that mandatory options exist and that argument number matches.
	 * 
	 * @throws ParseException
	 */
	private void checkOptionsIntegrity() throws CliParseException {
		for (CliOption cliOption : options) {
			if (!cliOption.isActive()) {
				continue;
			}
			NumberOfArguments noa = cliOption.getNumberOfArguments();
			if (NumberOfArguments.UNLIMITED.equals(noa)) {
				continue;
			}
			if (NumberOfArguments.NONE.equals(noa) && noa.ordinal() > 0) {
				failAndExit("Option '" + cliOption.getName()
						+ "' should have no arguments");
			}
			if ( cliOption.getValues().length != noa.ordinal()) {
				failAndExit("Option '" + cliOption.getName()
						+ "' should have "+noa.ordinal()+" arguments");
			}
		}
	}

	private void checkMandatoryOptions() throws CliParseException {
		for (CliOption cliOption : options) {
			if (cliOption.isMandatory() && !cliOption.isActive()) {
				failAndExit("Option '" + cliOption.getName() + "' is mandatory.");
			}
		}
	}
	
	private void registerActions(){
		for (CliOption cliOption : options) {
			if(cliOption instanceof CliAction && cliOption.isActive()){
				CliAction action = (CliAction) cliOption;
				action.injectOptions(options);
				registerAction(action);
			}
		}
	}
	
	private void registerAction(CliAction cliAction){
		if(activeActions == null){
			activeActions = new ArrayList<CliAction>();
		}
		activeActions.add(cliAction);
	}

	private void failAndExit(String s) throws CliParseException {
		throw new CliParseException(s);
	}

	public List<CliAction> getActiveActions(){
		if(activeActions == null)
			return Collections.emptyList();
		return activeActions;
	}
	
	private CliOption getOption(String opt) {
		CliOption cliOption = cliFlavor.matchOption(opt, options);
		if (cliOption == null)
			return null;
		cliOption.setActive(true);
		return cliOption;
	}
}
