package dnl.infra.cli;

/**
 * A <code>CliAction</code> is performed after all settings has been read.
 * 
 * @author Daniel Orr
 * 
 */
public class ListenerBasedCliAction extends CliAction {

	private CliActionListener actionListener;
	private CLI cli;

	public ListenerBasedCliAction(CLI cli, String name, CliActionListener actionListener) {
		super(name);
		this.actionListener = actionListener;
		this.cli = cli;
	}

	public ListenerBasedCliAction(CLI cli, String name, String description, CliActionListener actionListener) {
		super(name, description);
		this.actionListener = actionListener;
		this.cli = cli;
	}

	@Override
	public void executeAction() {
		actionListener.actionPerformed(cli);
	}
}
