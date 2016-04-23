package bezier.gui.menubar;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import bezier.EditorUI;

public class SaveAsMenuItem extends DialogMenuItem {
  public SaveAsMenuItem(EditorUI editor) {
    super("Save as", editor);
  }

  @Override
  protected boolean shouldOpenDialog(EditorUI editor) {
    return true;
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
