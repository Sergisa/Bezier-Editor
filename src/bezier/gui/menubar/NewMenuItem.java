package bezier.gui.menubar;

import bezier.EditorUI;

public class NewMenuItem extends AbstractMenuItem {
  public NewMenuItem(EditorUI editor) {
    super("New", editor);
  }

  @Override
  protected void doAction(EditorUI editor) {
    editor.getBezier().clearPoints();
    editor.setSaveLocation(null);
    editor.repaint();
  }
}
