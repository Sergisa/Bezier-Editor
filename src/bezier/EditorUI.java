package bezier;

import java.io.File;

public interface EditorUI {
	public Bezier getBezier();
	public File getSaveLocation();
	
	public void repaint();
	public void setBackgroundImage(File imageFile);
	
	public void setSaveLocation(File file); 
	
	public void exit();
}
