package dnl.infra.cli;

import org.junit.Ignore;

@Ignore
public class BaseParserTest {

	protected static Options createOptions(CliOption... options) {
		Options opts = new Options(options);
		opts.registerOption(new DummyHelpAction());
		return opts;
	}

	protected static class DummyHelpAction extends CliAction {

		public DummyHelpAction() {
			super("help");
		}

		@Override
		public void executeAction() {
			System.err.println("Help!");
		}

	}

	public BaseParserTest() {
		super();
	}

}