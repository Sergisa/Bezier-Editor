package bezier.gui.menubar;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import bezier.Bezier;
import bezier.EditorUI;

public abstract class DialogMenuItem extends AbstractMenuItem {
  private JFileChooser fileChooser;
  private String extension;
  
  protected DialogMenuItem(String name, EditorUI editor) {
    this (
        name,
        editor,
        new File(System.getProperty("user.dir") + "/" + Bezier.DEFAULT_SAVE_PATH),
        new FileNameExtensionFilter(
            "Curve file (*." + Bezier.FILE_EXTENSION + ")",
            Bezier.FILE_EXTENSION));
  }
  
  protected DialogMenuItem(String name, EditorUI editor, File defaultFolder, FileNameExtensionFilter chooserFilter) {
    super (name, editor);
    
    extension = chooserFilter.getExtensions()[0];
    
    defaultFolder.mkdirs();
    fileChooser = new JFileChooser(defaultFolder);
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(chooserFilter);
    fileChooser.setAcceptAllFileFilterUsed(true); // Put the 'all files'-filter at the bottom of the list
  }
  

  protected abstract boolean shouldOpenDialog(EditorUI editor);
  protected abstract int openDialog(EditorUI editor, JFileChooser chooser);
  protected abstract void performAction(EditorUI editor, File file) throws FileNotFoundException;
  protected abstract boolean shouldAddExtensionIfMissing();
  
  @Override
  protected void doAction(EditorUI editor) {
    File target = editor.getSaveLocation();
    int result = JFileChooser.APPROVE_OPTION;
    
    if (shouldOpenDialog(editor)) {
      result = openDialog(editor, fileChooser);
      if (result == JFileChooser.APPROVE_OPTION) {
        target = fileChooser.getSelectedFile();
        if (shouldAddExtensionIfMissing() && !target.getName().toLowerCase().endsWith("." + extension))
          target = new File(target.getAbsolutePath() + "." + extension);
      }
    }
    
    if (result == JFileChooser.APPROVE_OPTION) {
      try {
        performAction(editor, target);
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(
            (Component) editor,
            "Couldn't find the file: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
