package dnl.infra.cli;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SimpleHelpFormatter implements HelpFormatter {

	private boolean printTypes = true;
	private boolean sortAlphabetically = true;

	private String appName;

	public SimpleHelpFormatter() { 
	}

	public SimpleHelpFormatter(String appName, boolean printTypes) {
		this.printTypes = printTypes;
		this.appName = appName;
	}
	
	public SimpleHelpFormatter(String appName, boolean printTypes, boolean sortAlphabetically) {
		this.appName = appName;
		this.printTypes = printTypes;
		this.sortAlphabetically = sortAlphabetically;
	}

	protected void setPrintTypes(boolean printTypes) {
		this.printTypes = printTypes;
	}

	protected void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public void printHelp(Options options) {
		List<CliOption> optList = options.getAsList();
		if(sortAlphabetically){
			Collections.sort(optList, new Comparator<CliOption>() {
				@Override
				public int compare(CliOption o1, CliOption o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
		}
		
		String longest = "";
		for (CliOption cliOption : optList) {

			String s = cliOption.getName();
			if (printTypes && cliOption instanceof CliSetting) {
				CliSetting cs = (CliSetting) cliOption;
				s = cs.getName() + " <" + cs.getType() + ">";
			}

			if (s.length() > longest.length()) {
				longest = s;
			}
		}

		if(appName != null){
			System.out.print("usage: ");
			System.out.println(appName);
		}
		
		for (CliOption cliOption : optList) {
			print(" ");
			print("-");
			String s = cliOption.getName();
			if (printTypes && cliOption instanceof CliSetting) {
				CliSetting cs = (CliSetting) cliOption;
				if (cs.getType() != null) {
					s = cs.getName() + " <" + cs.getType() + ">";
				}
			}
			print(s);
			pad(longest.length() - s.length());
			print("    ");
			printDescription(cliOption.getDescription());
		}

	}

	private int l;

	private void print(String s) {
		l += s.length();
		System.out.print(s);
	}

	private void newLine() {
		l = 0;
		System.out.println();
	}

	private void pad(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(" ");
		}
		print(sb.toString());
	}

	private void printDescription(String d) {
		String desc = d == null ? "" : d;
		if (desc.length() + l < 80) {
			print(desc);
			newLine();
			return;
		}

		String s = desc;

		int indent = l;
		while (s.length() + l > 80) {
			s = printDescriptionLine(s);
			newLine();
			pad(indent);
		}
		if (s.length() > 0) {
			print(s);
			newLine();
		}
	}

	private String printDescriptionLine(String desc) {
		int ind = 80 - l;
		int i2 = getIndexOfClosestSpace(desc, ind);
		if (i2 > 0) {
			ind = i2 + 1;
		}

		String s1 = desc.substring(0, ind);
		String s2 = desc.substring(ind, desc.length());
		print(s1);

		return s2;
	}

	private int getIndexOfClosestSpace(String s, int pos) {
		for (int i = pos; i >= 0; i--) {
			if (s.charAt(i) == ' ') {
				return i;
			}
		}

		return -1;
	}

}
