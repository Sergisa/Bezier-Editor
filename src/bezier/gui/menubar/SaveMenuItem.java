package bezier.gui.menubar;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import bezier.EditorUI;

public class SaveMenuItem extends DialogMenuItem {
  public SaveMenuItem(EditorUI editor) {
    super("Save", editor);
    setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
  }

  @Override
  protected boolean shouldOpenDialog(EditorUI editor) {
    return editor.getSaveLocation() == null;
  }
  
  @Override
  protected boolean shouldAddExtensionIfMissing() {
    return true;
  }

  @Override
  protected int openDialog(EditorUI editor, JFileChooser chooser) {
    return chooser.showDialog((Component) editor, "Save curve");
  }

  @Override
  protected void performAction(EditorUI editor, File file) throws FileNotFoundException {
    editor.getBezier().save(file);
    editor.setSaveLocation(file);
  }
}
