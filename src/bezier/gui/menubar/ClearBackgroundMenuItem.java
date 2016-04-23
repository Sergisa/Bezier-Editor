package bezier.gui.menubar;

import bezier.EditorUI;

public class ClearBackgroundMenuItem extends AbstractMenuItem {
  public ClearBackgroundMenuItem(EditorUI editor) {
    super ("Clear background", editor);
  }
  
  @Override
  protected void doAction(EditorUI editor) {
    editor.setBackgroundImage(null);
    editor.repaint();
  }
}

