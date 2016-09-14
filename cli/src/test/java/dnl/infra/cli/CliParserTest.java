package dnl.infra.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CliParserTest extends BaseParserTest {

	@Test(expected = CliParseException.class)
	public void testMissingDash() throws CliParseException {
		CliFlag flag1 = new CliFlag("flag1");
		CliParser cliParser = new CliParser(CliFlavorType.SIMPLE);
		Options options = createOptions(new CliOption[] { flag1 });
		cliParser.parseCli(options, new String[] { "flag1" });
	}

	@Test
	public void testTwoFlags() throws CliParseException {
		CliFlag flag1 = new CliFlag("flag1");
		CliFlag flag2 = new CliFlag("flag2");
		CliSetting setting1 = new CliSetting("setting1");
		CliParser cliParser = new CliParser(CliFlavorType.SIMPLE);
		Options options = createOptions(new CliOption[] { flag1, flag2, setting1 });
		cliParser.parseCli(options, new String[] { "-flag1", "-flag2" });
		assertTrue(flag1.isFlagSet());
		assertTrue(flag2.isFlagSet());
	}

	@Test(expected = CliParseException.class)
	public void testTwoFlagsWithUnnecessaryArg() throws CliParseException {
		CliFlag flag1 = new CliFlag("flag1");
		CliFlag flag2 = new CliFlag("flag2");
		CliSetting setting1 = new CliSetting("setting1");
		CliParser cliParser = new CliParser(CliFlavorType.SIMPLE);
		Options options = createOptions(new CliOption[] { flag1, flag2, setting1 });
		cliParser.parseCli(options, new String[] { "-flag1", "aba", "-flag2" });
		assertTrue(flag1.isFlagSet());
		assertTrue(flag2.isFlagSet());
	}

	@Test(expected = CliParseException.class)
	public void testMissingMandatorySetting() throws CliParseException {
		CliFlag flag1 = new CliFlag("flag1");
		CliFlag flag2 = new CliFlag("flag2");
		CliSetting setting1 = OptionFactory.createMandatorySetting("setting1");
		CliParser cliParser = new CliParser(CliFlavorType.SIMPLE);
		Options options = createOptions(new CliOption[] { flag1, flag2, setting1 });
		cliParser.parseCli(options, new String[] { "-flag1", "-flag2" });
	}

	@Test(expected = CliParseException.class)
	public void testMissingArg() throws CliParseException {
		CliFlag flag1 = new CliFlag("flag1");
		CliFlag flag2 = new CliFlag("flag2");
		CliSetting setting1 = OptionFactory.createMandatorySetting("setting1");
		CliParser cliParser = new CliParser(CliFlavorType.SIMPLE);
		Options options = createOptions(new CliOption[] { flag1, flag2, setting1 });
		cliParser.parseCli(options, new String[] { "-flag1", "-flag2", "-setting1" });
	}

	@Test
	public void testCorrectArgValue() throws CliParseException {
		CliFlag flag1 = new CliFlag("flag1");
		CliFlag flag2 = new CliFlag("flag2");
		CliSetting setting1 = OptionFactory.createMandatorySetting("setting1");
		CliParser cliParser = new CliParser(CliFlavorType.SIMPLE);
		Options options = createOptions(new CliOption[] { flag1, flag2, setting1 });
		cliParser.parseCli(options, new String[] { "-flag1", "-flag2", "-setting1", "717" });
		assertEquals("717", setting1.getValue());
	}
}
