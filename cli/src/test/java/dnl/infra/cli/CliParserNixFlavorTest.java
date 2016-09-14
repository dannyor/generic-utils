package dnl.infra.cli;

import static org.junit.Assert.*;

import org.junit.Test;


public class CliParserNixFlavorTest extends BaseParserTest {
	
	@Test
	public void testCorrectArgValue() throws CliParseException {
		CliFlag flag1 = OptionFactory4NixFlavor.createFlag("flag1", "f", "");
		CliFlag flag2 = OptionFactory4NixFlavor.createFlag("flag2", "g", "");
		CliSetting setting1 = OptionFactory4NixFlavor.createMandatorySetting("setting1", "S");
		CliParser cliParser = new CliParser(CliFlavorType.STANDARD_NIX);
		Options options = createOptions(new CliOption[] { flag1, flag2, setting1 });
		cliParser.parseCli(options, new String[] {"-f", "-g", "-S", "717"});
		assertEquals("717", setting1.getValue());
		assertTrue(flag1.isActive());
		assertTrue(flag2.isActive());
	}

	@Test
	public void testMixShortAndLong() throws CliParseException {
		CliFlag flag1 = OptionFactory4NixFlavor.createFlag("flag1", "f", "");
		CliFlag flag2 = OptionFactory4NixFlavor.createFlag("flag2", "g", "");
		CliFlag flag3 = OptionFactory4NixFlavor.createFlag("flag3", "z", "");
		CliSetting setting1 = OptionFactory4NixFlavor.createMandatorySetting("setting1", "S");
		CliParser cliParser = new CliParser(CliFlavorType.STANDARD_NIX);
		Options options = createOptions(new CliOption[] { flag1, flag2, flag3, setting1 });
		cliParser.parseCli(options, new String[] {"--flag1", "-g", "--setting1", "717"});
		assertEquals("717", setting1.getValue());
		assertTrue(flag1.isActive());
		assertTrue(flag2.isActive());
		assertFalse(flag3.isActive());
	}
	
}
