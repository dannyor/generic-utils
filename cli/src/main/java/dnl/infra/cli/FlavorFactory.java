package dnl.infra.cli;


/**
 * 
 * @author Daniel Orr
 *
 */
public class FlavorFactory {

	/**
	 * 
	 * @param flavorType
	 * @return
	 */
	public static CliFlavor getFlavor(CliFlavorType flavorType){
		switch (flavorType) {
		case STANDARD_NIX:
			return new StandardNixFlavor();
		case SIMPLE:
			return new SimpleFlavor();
		}
		return null;
	}
}
