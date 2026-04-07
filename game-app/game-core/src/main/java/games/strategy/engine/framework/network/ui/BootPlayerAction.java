package games.strategy.engine.framework.network.ui;

import games.strategy.net.IServerMessenger;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/** An action for booting a player from a network game. */
public class BootPlayerAction extends AbstractAction {
  private static final long serialVersionUID = 2799566047887167058L;
  private final Component parent;
  private final IServerMessenger messenger;

  public BootPlayerAction(final Component parent, final IServerMessenger messenger) {
    super("Remove Player");
    this.parent = JOptionPane.getFrameForComponent(parent);
    this.messenger = messenger;
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    PlayerSelectionUi.selectPlayer(parent, messenger, "Select player to remove")
        .ifPresent(messenger::removeConnection);
  }
}
