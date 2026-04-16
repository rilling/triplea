package games.strategy.engine.framework.network.ui;

import games.strategy.net.IServerMessenger;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/** An action for banning a player from a network game. */
public class BanPlayerAction extends AbstractAction {
  private static final long serialVersionUID = -2415917785233191860L;
  private final Component parent;
  private final IServerMessenger messenger;

  public BanPlayerAction(final Component parent, final IServerMessenger messenger) {
    super("Ban Player From Game");
    this.parent = JOptionPane.getFrameForComponent(parent);
    this.messenger = messenger;
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    PlayerSelectionUi.selectPlayer(parent, messenger, "Select player to ban")
        .ifPresent(
            node -> {
              final String ip = node.getAddress().getHostAddress();
              final String mac = messenger.getPlayerMac(node.getPlayerName());
              messenger.banPlayer(ip, mac);
              messenger.removeConnection(node);
            });
  }
}
