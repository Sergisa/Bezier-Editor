package bezier.gui.menubar;

import bezier.EditorUI;

public class ExitMenuItem extends AbstractMenuItem {
    public ExitMenuItem(EditorUI editor) {
        super("Exit", editor);
    }

    @Override
    protected void doAction(EditorUI editor) {
        editor.exit();
    }
}

