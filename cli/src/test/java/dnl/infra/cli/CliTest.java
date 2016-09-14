package dnl.infra.cli;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CliTest {

	@Test
	public void test() {
		CLI cli = new CLI();
		String infoDesc = "prints all kinds of info regarding entities and sessions";
		String hostDesc = "connects to the spedified host";
		String deleteDesc = "deletes the session with the given name";
		String delProjDesc = "deletes all projections of given type";
		String delResultsDesc = "deletes all results by category";
		String sessInfoDesc = "prints all info regarding sessions";
		String listExDesc = "list exeptions by module (VM/EM/RM/ALL)";
		

		cli.addTimeRangeSetting("t", "[dd.MM.yyyy-dd.MM.yyyy] a time range", false);
		cli.addAction("info", infoDesc, new CliActionListener() {
			@Override
			public void actionPerformed(CLI cli) {
				
			}
		});
		cli.addSetting("host", "url", hostDesc, true);
		cli.addAction("delete", "sessionName", deleteDesc, new DummyListener());
		cli.addAction("delete_projection", "objectType", delProjDesc,
				new DummyListener());
		cli.addAction("delete_results", "category", delResultsDesc, new DummyListener());
		cli.addAction("exceptions", "module", listExDesc, new DummyListener());
		cli.addAction("sessDef", "file", "session definition", new DummyListener());
		cli.addAction("sessionsInfo", sessInfoDesc, new DummyListener());
		cli.addAction("createUser", "create user", new DummyListener());
		cli.addAction("registerAlert", "register alert", new DummyListener());
		cli.addAction("testEmail", "emailAddress", "send test email", new DummyListener());
		cli.addAction("runScript", "file", "groovy file", new DummyListener());
		cli.addAction("runScriptInTransaction", "file", "groovy file",
				new DummyListener());
		cli.addAction("setConfigParam", "SECTION/PARAM=VALUE", "sets config value",
				new DummyListener());
		cli.addAction("chartConfig", "Id/Title/yLabel/xLabel", "config for charts",
				new DummyListener());

		cli.parseCli(new String[]{"-help"});
		

	}

	@Test
	public void test2() {
		CLI cli = new CLI();
		String infoDesc = "prints all kinds of info regarding entities and sessions";
		String hostDesc = "connects to the spedified host";
		String deleteDesc = "deletes the session with the given name";
		String delProjDesc = "deletes all projections of given type";
		String delResultsDesc = "deletes all results by category";
		String sessInfoDesc = "prints all info regarding sessions";
		String listExDesc = "list exeptions by module (VM/EM/RM/ALL)";
		
		
		cli.addTimeRangeSetting("t", "[dd.MM.yyyy-dd.MM.yyyy] a time range", false);
		cli.addAction("info", infoDesc, new CliActionListener() {
			@Override
			public void actionPerformed(CLI cli) {
				
			}
		});

		cli.addSetting("host", "url", hostDesc, true);
		cli.addAction("delete", "sessionName", deleteDesc, new DummyListener());
		cli.addAction("delete_projection", "objectType", delProjDesc,
				new DummyListener());
		cli.addAction("delete_results", "category", delResultsDesc, new DummyListener());
		cli.addAction("exceptions", "module", listExDesc, new DummyListener());
		cli.addAction("sessDef", "file", "session definition", new DummyListener());
		cli.addAction("sessionsInfo", sessInfoDesc, new DummyListener());
		cli.addAction("createUser", "create user", new DummyListener());
		cli.addAction("registerAlert", "register alert", new DummyListener());
		cli.addAction("testEmail", "emailAddress", "send test email", new DummyListener());
		cli.addAction("runScript", "file", "groovy file", new DummyListener());
		cli.addAction("runScriptInTransaction", "file", "groovy file",
				new DummyListener());
		cli.addAction("setConfigParam", "SECTION/PARAM=VALUE", "sets config value",
				new DummyListener());
		cli.addAction("chartConfig", "Id/Title/yLabel/xLabel", "config for charts",
				new DummyListener());
		
		cli.parseCli(new String[]{});
		
		
	}

	private static class DummyListener implements CliActionListener {
		@Override
		public void actionPerformed(CLI cli) {
		}

	}

}
