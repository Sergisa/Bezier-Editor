package bezier;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bezier {
	public static String FILE_EXTENSION = "points";
	public static String DEFAULT_SAVE_PATH = "curves";
  private static int POINT_SIZE = 10;
  
  private List<Point2D> controlPoints;
  
  
  public Bezier() {
    controlPoints = new ArrayList<Point2D>();
	}
  
  
  public void addPoint(Point2D point) {
  	if (controlPoints.size() >= 2) {
  		Rectangle2D bounds = new Rectangle2D.Double(point.getX() - POINT_SIZE, point.getY() - POINT_SIZE, POINT_SIZE*2, POINT_SIZE*2);
  		Point2D p1 = controlPoints.get(0);
  		for (int i = 1; i < controlPoints.size(); i++) {
  			Point2D p2 = controlPoints.get(i);
				if (bounds.intersectsLine(p1.getX(), p1.getY(), p2.getX(), p2.getY())) {
					controlPoints.add(i, point);
					return;
				}
				p1 = p2;
			}
  	}
  	controlPoints.add(point);
  }
  
  public void removePointAt(Point2D point) {
    Point2D controlPoint  = findPointAt(point);
    if (controlPoint != null) {
    	controlPoints.remove(controlPoint);
    }
  }
  
  public Point2D findPointAt(Point2D point) {
  	for (Point2D controlPoint : controlPoints) {
      Rectangle2D bounds = new Rectangle2D.Double(controlPoint.getX() - POINT_SIZE, controlPoint.getY() - POINT_SIZE, POINT_SIZE*2, POINT_SIZE*2);
      if (bounds.contains(point)) {
        return controlPoint;
      }
    }
  	return null;
  }
  
  public void clearPoints() {
  	controlPoints.clear();
  }
  
  public boolean hasPoints() {
  	return !controlPoints.isEmpty();
  }
  
  
  public void render(Graphics graphics) {
  	if (!controlPoints.isEmpty()) {
      renderControlPoints(graphics);
      renderBezierCurve(graphics);
    }
  }

	private void renderControlPoints(Graphics graphics) {
		Point2D lastPoint = controlPoints.get(0);
		for (Point2D point : controlPoints)
		{
		  graphics.setColor(Color.CYAN);
		  drawLine(graphics, lastPoint, point);
		  graphics.setColor(Color.BLUE);
		  drawPoint(graphics, point);
		  lastPoint = point;
		}
	}
  
  private void renderBezierCurve(Graphics g) {
    final int numberOfSteps = 250;
    
    List<Point2D> list = new ArrayList<Point2D>();
    List<Point2D> result = new ArrayList<Point2D>();
		for (int k = 0; k <= numberOfSteps; k++)
    {
    	double t = (double)k/numberOfSteps;
    	
      list.clear();
      list.addAll(controlPoints);
      for (int i = 0; i < list.size(); i++)
      {
        for (int j = 0; j < list.size() - i - 1; j++)
        {
          Point2D p1 = list.get(j);
          Point2D p2 = list.get(j+1);
          Point2D n = new Point2D.Double(
          		((1-t)*p1.getX() + t*p2.getX()), 
          		((1-t)*p1.getY() + t*p2.getY()));
          list.set(j, n);
        }
      }
      
      result.add(list.get(0));
    }

    Point2D lastPoint = result.get(0);
    for (Point2D point : result)
    {
      g.setColor(Color.BLACK);
      drawLine(g, lastPoint, point);
      lastPoint = point;
    }
  }

  private void drawPoint(Graphics g, Point2D point) {
    g.fillOval((int)point.getX() - POINT_SIZE/2, (int)point.getY() - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
  }

  private void drawLine(Graphics g, Point2D a, Point2D b) {
    g.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
  }
  
  
  public void save(File saveFile) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
      for (Point2D point2d : controlPoints)
      {
        writer.write(point2d.getX() + " " + point2d.getY() + "\r\n");
      }
      writer.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
//    StringBuilder builder = new StringBuilder();
//    for (Point2D point2d : controlPoints)
//    {
//      builder.append((int)point2d.getX() + " " + (int)(backgroundImage.getHeight() - point2d.getY()) + " ");
//    }
//    System.out.println("Points: " + builder.toString());
  }
  
  
  public void load(File sourceFile) {
    try {
      if (sourceFile.exists()) {
      	clearPoints();
        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        while (reader.ready()) {
          String line = reader.readLine();
          String[] data = line.split(" ");
          if (data.length > 1) {
            Point2D point = new Point2D.Double(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
            controlPoints.add(point);
          }
        }
        reader.close();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
