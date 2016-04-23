package bezier.gui.menubar;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import bezier.EditorUI;

public class OpenBackgroundImageMenuItem extends DialogMenuItem {
  public OpenBackgroundImageMenuItem(EditorUI editor) {
    super(
    		"Select background", 
    		editor,
    		new File(System.getProperty("user.dir")),
        new FileNameExtensionFilter(
            "Image files",
            "png", "jpg", "bmp"));
    setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
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
    return chooser.showDialog((Component) editor, "Select editor background");
  }

  @Override
  protected void performAction(EditorUI editor, File file) throws FileNotFoundException {
    if (!file.exists()) {
      throw new FileNotFoundException(file.getName());
    }
    
    editor.setBackgroundImage(file);
    editor.repaint();
  }
}
