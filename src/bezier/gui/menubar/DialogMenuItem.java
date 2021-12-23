package bezier.gui.menubar;

import bezier.Bezier;
import bezier.EditorUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class DialogMenuItem extends AbstractMenuItem {
    private final JFileChooser fileChooser;
    private final String extension;

    protected DialogMenuItem(String name, EditorUI editor) {
        this(
                name,
                editor,
                new File(System.getProperty("user.dir") + "/" + Bezier.DEFAULT_SAVE_PATH),
                new FileNameExtensionFilter(
                        "Curve file (*." + Bezier.FILE_EXTENSION + ")",
                        Bezier.FILE_EXTENSION));
    }

    protected DialogMenuItem(String name, EditorUI editor, File defaultFolder, FileNameExtensionFilter chooserFilter) {
        super(name, editor);

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
