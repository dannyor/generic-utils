package dnl.infra.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Daniel Orr
 *
 */
public class Options implements Iterable<CliOption> {

	private Map<String, CliOption> options = new HashMap<String, CliOption>();

	public Options() {
	}

	public Options(List<CliOption> optList) {
		for (CliOption cliOption : optList) {
			registerOption(cliOption);
		}
	}

	public Options(CliOption[] optList) {
		for (CliOption cliOption : optList) {
			registerOption(cliOption);
		}
	}

	public void registerOption(CliOption cliOption) {
		options.put(cliOption.getName(), cliOption);
	}

	public CliOption getOption(String name) {
		return options.get(name);
	}

	public List<CliOption> getAsList() {
		return new ArrayList<CliOption>(options.values());
	}

	@Override
	public Iterator<CliOption> iterator() {
		return options.values().iterator();
	}
}
