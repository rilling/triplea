package games.strategy.engine.framework.network.ui;

import games.strategy.net.INode;
import games.strategy.net.IServerMessenger;
import java.awt.Component;
import java.util.Optional;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/** Shared UI helper for selecting a remote player from a network game. */
class PlayerSelectionUi {

  private PlayerSelectionUi() {}

  /**
   * Prompts the host to select a remote player from the current game session.
   *
   * @param parent the parent component for dialogs
   * @param messenger the server messenger providing node information
   * @param dialogTitle the title shown on the player selection dialog
   * @return the selected {@link INode}, or empty if cancelled or no remote players exist
   */
  static Optional<INode> selectPlayer(
      final Component parent, final IServerMessenger messenger, final String dialogTitle) {
    final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    final JComboBox<String> combo = new JComboBox<>(model);
    model.addElement("");
    for (final INode node : new TreeSet<>(messenger.getNodes())) {
      if (!node.equals(messenger.getLocalNode())) {
        model.addElement(node.getName());
      }
    }
    if (model.getSize() == 1) {
      JOptionPane.showMessageDialog(
          parent, "No remote players", "No Remote Players", JOptionPane.ERROR_MESSAGE);
      return Optional.empty();
    }
    final int selectedOption =
        JOptionPane.showConfirmDialog(parent, combo, dialogTitle, JOptionPane.OK_CANCEL_OPTION);
    if (selectedOption != JOptionPane.OK_OPTION) {
      return Optional.empty();
    }
    final String name = (String) combo.getSelectedItem();
    return messenger.getNodes().stream().filter(n -> n.getName().equals(name)).findFirst();
  }
}
