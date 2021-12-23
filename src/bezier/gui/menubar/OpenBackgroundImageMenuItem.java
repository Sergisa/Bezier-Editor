package bezier.gui.menubar;

import bezier.EditorUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

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
