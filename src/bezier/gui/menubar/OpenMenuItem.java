package bezier.gui.menubar;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import bezier.Bezier;
import bezier.EditorUI;

public class OpenMenuItem extends DialogMenuItem {
  public OpenMenuItem(EditorUI editor) {
    super("Open", editor);
    setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
  }

  @Override
  protected boolean shouldOpenDialog(EditorUI editor) {
    return true;
  }
  
  @Override
  protected boolean shouldAddExtensionIfMissing() {
    return false;
  }

  @Override
  protected int openDialog(EditorUI editor, JFileChooser chooser) {
    return chooser.showDialog((Component) editor, "Open curve file");
  }

  @Override
  protected void performAction(EditorUI editor, File file) throws FileNotFoundException {
    if (!file.exists()) {
      file = new File(file.getAbsolutePath() + "." + Bezier.FILE_EXTENSION);
      if (!file.exists())
        throw new FileNotFoundException(file.getName().substring(0, file.getName().length() - Bezier.FILE_EXTENSION.length() - 1));
    }
    
    editor.getBezier().load(file);
    editor.setSaveLocation(file);
    editor.repaint();
  }
}
