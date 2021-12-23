package bezier.gui.menubar;

import bezier.EditorUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractMenuItem extends JMenuItem {
    private final EditorUI editor;

    public AbstractMenuItem(String text, EditorUI editor) {
        super(text);

        this.editor = editor;

        addActionListener(actionListener);
    }

    protected abstract void doAction(EditorUI editor);


    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            doAction(editor);
        }
    };
}
