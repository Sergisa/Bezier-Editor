package bezier.gui.menubar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import bezier.EditorUI;

public class MenuBar extends JMenuBar {
	public MenuBar(EditorUI editor) {
    JMenu fileMenu = new JMenu("File");
    fileMenu.add(new NewMenuItem(editor));
    fileMenu.add(new OpenMenuItem(editor));
    fileMenu.addSeparator();
    fileMenu.add(new SaveMenuItem(editor));
    fileMenu.add(new SaveAsMenuItem(editor));
    fileMenu.addSeparator();
    fileMenu.add(new ExitMenuItem(editor));
    add(fileMenu);

    JMenu viewMenu = new JMenu("View");
    viewMenu.add(new OpenBackgroundImageMenuItem(editor));
    add(viewMenu);
	}
}
