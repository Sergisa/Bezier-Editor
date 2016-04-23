package bezier.gui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import bezier.EditorUI;

public abstract class AbstractMenuItem extends JMenuItem
{
  private EditorUI editor;
  
  public AbstractMenuItem(String text, EditorUI editor)
  {
    super (text);
    
    this.editor = editor;
    
    addActionListener(actionListener);
  }
  
  protected abstract void doAction(EditorUI editor);
  
  
  private ActionListener actionListener = new ActionListener()
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      doAction(editor);
    }
  };
}
