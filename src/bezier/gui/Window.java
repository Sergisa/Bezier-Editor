package bezier.gui;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import bezier.Bezier;
import bezier.EditorUI;
import bezier.gui.menubar.MenuBar;


public class Window extends JFrame implements EditorUI {
	
	private Editor editor;
	
	private File saveLocation;
	
  public Window() {
  	setTitle("Bezier curve editor");
  	
  	Image icon = loadIcon("icon.png");
    if (icon != null)
      setIconImage(icon);
  	
    editor = new Editor();
    add(editor);
    
    setJMenuBar(new MenuBar(this));
    
    pack();
    
    addWindowListener(windowListener);
    
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  private Image loadIcon(String path) {
  	try {
			return ImageIO.read(ClassLoader.getSystemResourceAsStream(path));
		} catch (IOException e) {
      System.out.println("Failed to load icon file!");
		} catch (IllegalArgumentException e) {
      System.out.println("Failed to load icon file!");
		}
  	return null;
  }
  

	@Override
	public Bezier getBezier() {
		return editor.getBezier();
	}

	@Override
	public File getSaveLocation() {
		return saveLocation;
	}

	@Override
	public void setBackgroundImage(File imageFile) {
		editor.setBackgroundImage(imageFile);
	}

	@Override
	public void setSaveLocation(File file) {
		if (file == null)
	  	setTitle("Bezier curve editor");
		else
	  	setTitle("Bezier curve editor - " + file.getPath());
		saveLocation = file;
	}

	@Override
	public void exit() {
		if (saveLocation == null && editor.getBezier().hasPoints()) {
			int answer = JOptionPane.showConfirmDialog(this, "Your curve doesn't have a save file, exit anyway?", "Are you sure?", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
				dispose();
		} else {
			dispose();
		}
	}
	
	
	private WindowListener windowListener = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			exit();
		}
	};
}
