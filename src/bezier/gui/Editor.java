package bezier.gui;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bezier.Bezier;


public class Editor extends JPanel {
  private static final File CURRENT_CURVE_BACKUP_FILE = new File("current." + Bezier.FILE_EXTENSION);
  
  private BufferedImage backgroundImage;
  
  private Bezier bezier;
  
  private Point2D selectedPoint;
  
  private Point2D viewportOffset;
  private boolean isOffsetingViewport;
  private Point lastMousePosition;
  
  
  public Editor() {
    bezier = new Bezier();
    viewportOffset = new Point2D.Float();
    
    setPreferredSize(new Dimension(800, 600));
    
    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
  }
  
  
	public Bezier getBezier() {
		return bezier;
	}

	public void setBackgroundImage(File imageFile) {
		if (imageFile == null) {
			backgroundImage = null;
		} else {
			try {
	      backgroundImage = ImageIO.read(imageFile);
	      setPreferredSize(new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight()));
	    } catch (IOException e) {
	      JOptionPane.showMessageDialog(getParent(), "Failed to load imag!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
		}
	}
  
  
  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    setGraphicsConfiguration(graphics);
    
    graphics.translate((int)viewportOffset.getX(), (int)viewportOffset.getY());
    graphics.drawImage(backgroundImage, 0, 0, null);
    bezier.render(graphics);
    graphics.translate(-(int)viewportOffset.getX(), -(int)viewportOffset.getY());
    
    renderUsageHints(graphics);
  }

	private void setGraphicsConfiguration(Graphics graphics) {
		Graphics2D g2d = (Graphics2D)graphics;
	  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	  g2d.setStroke(new BasicStroke(2));
	}

	private void renderUsageHints(Graphics graphics) {
		Font font = graphics.getFont().deriveFont(Font.BOLD);
    graphics.setFont(font);
    
    FontMetrics metrics = graphics.getFontMetrics();
    renderString(graphics, metrics, "LMB - Place/Move control points", 0);
    renderString(graphics, metrics, "RMB - Remove control points", 1);
    renderString(graphics, metrics, "MMB - Pan the editor surface", 2);
	}

	private void renderString(Graphics graphics, FontMetrics metrics, String string, int lineNumber) {
		graphics.drawString(
				string, 
				getWidth() - metrics.stringWidth(string) - 3, 
				lineNumber * metrics.getHeight() + metrics.getAscent());
	}

	
	private MouseAdapter mouseListener = new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
      Point mouse = new Point(e.getPoint().x - (int)viewportOffset.getX(), e.getPoint().y - (int)viewportOffset.getY());
      
      if (selectedPoint == null) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          if (!selectExistingPointAt(mouse)) {
          	selectedPoint = mouse;
            bezier.addPoint(mouse);
            bezier.save(CURRENT_CURVE_BACKUP_FILE);
            repaint();
          }
        } else if (e.getButton() == MouseEvent.BUTTON2) {
          isOffsetingViewport = true;
          lastMousePosition = e.getPoint();

        } else if (e.getButton() == MouseEvent.BUTTON3) {
          bezier.removePointAt(mouse);
          bezier.save(CURRENT_CURVE_BACKUP_FILE);
          repaint();
        }
      }
    }

    private boolean selectExistingPointAt(Point mouse) {
      selectedPoint = bezier.findPointAt(mouse);
      return selectedPoint != null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
      Point mouse = new Point(e.getPoint().x - (int)viewportOffset.getX(), e.getPoint().y - (int)viewportOffset.getY());
      if (selectedPoint != null) {
        selectedPoint.setLocation(mouse);
        repaint();
      }
      if (isOffsetingViewport) {
        viewportOffset.setLocation(viewportOffset.getX() + e.getX() - lastMousePosition.getX(), viewportOffset.getY() + e.getY() - lastMousePosition.getY());
        lastMousePosition.setLocation(e.getPoint());
        repaint();
      }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) {
        if (selectedPoint != null)
          bezier.save(CURRENT_CURVE_BACKUP_FILE);
        selectedPoint = null;
      }
      if (e.getButton() == MouseEvent.BUTTON2)
        isOffsetingViewport = false;
    }
  };
}
