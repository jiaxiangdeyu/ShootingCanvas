/**
 * 
 */
package pete.study.practice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Wenping
 *
 */
class CalibrationPoint {
	
	private boolean isVisiable;
	private int centerX;
	private int centerY;
	private int pointX;
	private int pointY;
	private double referenceLength;
	
	public boolean isVisiable() {
		return isVisiable;
	}
	public void setVisiable(boolean isVisiable) {
		this.isVisiable = isVisiable;
	}
	public int getCenterX() {
		return centerX;
	}
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}
	public int getPointX() {
		return pointX;
	}
	public void setPointX(int pointX) {
		this.pointX = pointX;
	}
	public int getPointY() {
		return pointY;
	}
	public void setPointY(int pointY) {
		this.pointY = pointY;
	}
	
	public CalibrationPoint(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.pointX = -1;
		this.pointY = -1;
		this.isVisiable = true;
		this.referenceLength = ConfigParameters.getScreenDPI() / (2.54 * 10);
	}
	
	public static Vector<CalibrationPoint> generateCalibrations(int width, int height) {
		
		Vector<CalibrationPoint> calibrationList = new Vector<CalibrationPoint>();
		// Fixed five calibration points
		calibrationList.add(new CalibrationPoint(50, 50));
		calibrationList.add(new CalibrationPoint(width - 50, 50));
		calibrationList.add(new CalibrationPoint(width - 50, height - 50));
		calibrationList.add(new CalibrationPoint(50, height - 50));
		calibrationList.add(new CalibrationPoint(width / 2, height / 2));
		
		// Dynamic fifteen calibration points
		Random rand = new Random();
		int i = 0;
		while(i < 15) {
			int x = rand.nextInt(width - 102) + 51;
			int y = rand.nextInt(height - 102) + 51;
			if (x == width / 2 && y == height / 2)
				continue;
			calibrationList.add(new CalibrationPoint(x, y));
			i++;
		}
		return calibrationList;
	}
	public boolean isSelected(int pointX, int pointY) {
		double dist = Math.sqrt((centerX - pointX)*(centerX - pointX) + (centerY - pointY) * (centerY - pointY));
		return dist <= this.referenceLength;
	}
	
	public double getCalibrationError() {
		return Math.sqrt((centerX - pointX)*(centerX - pointX) + (centerY - pointY) * (centerY - pointY));
	}
	
	public void draw(Graphics g) {
		if (isVisiable) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(1.0f));

			// Pixels per mm
			int radius = (int) Math.ceil(referenceLength);
			int leftUpperX = centerX - radius;
			int leftUpperY = centerY - radius;
			Ellipse2D ellipse = new Ellipse2D.Double(leftUpperX, leftUpperY, radius * 2, radius *2);
			g2d.fill(ellipse);			
			
/*			Line2D vLine = new Line2D.Double(centerX, centerY - 4, centerX, centerY + 4);
			g2d.draw(vLine);
			Line2D hLine = new Line2D.Double(centerX - 4, centerY, centerX + 4, centerY);
			g2d.draw(hLine);*/
		}
	}
}
public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private int clickType; // 0 Left, 1 Right Button
	private Cursor crossCursor, defaultCursor;
	private boolean dataStateFlag;
	private int currentIndex;
	private Vector<ShapeInfo> shapeList;
	
	private boolean isCalibration;
	private Vector<CalibrationPoint> calibrationList;
	private int calibrationCount;
	private double calibrationError;
	
	public Vector<ShapeInfo> getShapeList() {
		return this.shapeList;
	}
	public double GetCalibrationError() {
		return this.calibrationError;
	}
	public CanvasPanel() {
		super();
		clickType = 0;
		defaultCursor = Cursor.getDefaultCursor();
		crossCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		dataStateFlag = false; // No need to save these circle informations.
		currentIndex = -1;
		shapeList = new Vector<ShapeInfo>();
		
		isCalibration = false;
		calibrationList = new Vector<CalibrationPoint>();
		calibrationCount = 0;
		calibrationError = 1; // Set 1 pixel for error by default
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	public void resetCalibrationParameters() {
		isCalibration = true;
		Vector<CalibrationPoint> calibrations = CalibrationPoint.generateCalibrations(this.getWidth(), this.getHeight());
		synchronized(this) {
			calibrationList.clear();
			calibrationList = calibrations;
			calibrationCount = 0;
			calibrationError = 0.0;
			this.setCursor(crossCursor);
		}
		this.repaint();
	}
	public void resetDrawParameters(GameParameters para) {
		
		isCalibration = false;
		// mouse left button or mouse right button
		clickType = para.getClickTypeFlag();
		
		ShapeGenerator generator = new ShapeGenerator(para, this.getWidth(), this.getHeight());
		generator.generateShape();
		Vector<ShapeInfo> newShapeList = generator.getShapeList();
		synchronized(this) {
			this.shapeList.clear();
			this.shapeList = newShapeList;
			this.currentIndex = 0;
			this.dataStateFlag = false;
			this.setCursor(defaultCursor);
		}
		this.repaint();
	}

	public boolean dataHasChanged() {
		return dataStateFlag;
	}
	
	// Draw Standard Points for Testing
	private void drawCalibrationPoints(Graphics g) {
		if (!isCalibration)
			return;
		for (CalibrationPoint cp : this.calibrationList) {
			cp.draw(g);
		}
	}
	// Draw Games
	private void drawGameContent(Graphics g) {
		if (isCalibration)
			return;
		if (currentIndex >= 0 && currentIndex < shapeList.size()) {
			ShapeInfo shape = shapeList.get(currentIndex);
			shape.drawShape(g);
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawCalibrationPoints(g);
		drawGameContent(g);
	}

	private void mouseClickedDuringCalibration(MouseEvent event) {
		if (!isCalibration)
			return;
		if (this.calibrationCount >= 20) { // Finished calibration
			return;
		}
		int pointX = event.getX();
		int pointY = event.getY();
		int count = 0;
		for (CalibrationPoint cp : this.calibrationList) {
			if (cp.isVisiable()) {
				boolean isSelected = cp.isSelected(pointX, pointY);
				if (isSelected) {
					calibrationCount++; // we have calibration one point.
					cp.setPointX(pointX);
					cp.setPointY(pointY);
					cp.setVisiable(false); // set this invisible
					// For Debugging
					System.out.print("(" + cp.getCenterX() + ", " +cp.getCenterY() + ") (" 
										 + pointX + ", " + pointY + ") Distance: " + cp.getCalibrationError());
					System.out.println();
					this.repaint();
				}
				else
					count++; // visible calibration left counter
			}
		}
		if (this.calibrationCount >= 20 && count == 0) {
			double error = 0.0;
			for (CalibrationPoint cp : calibrationList)
				error += cp.getCalibrationError();
			this.calibrationError = error / calibrationList.size();
			System.out.println("The calibration error is: " + calibrationError + " pixels.");
			this.setCursor(defaultCursor);
			double errorInMM = (calibrationError * 25.4)/ ConfigParameters.getScreenDPI();
			JOptionPane.showConfirmDialog(this, String.format("Calibration Success, and the calibration Error is %.2f mm!", errorInMM), "Inform", JOptionPane.PLAIN_MESSAGE);
			return;
		}
	}
	private void mouseClickedDuringGame(MouseEvent event) {
		if (isCalibration)
			return;
		if (shapeList == null || shapeList.isEmpty() || !(currentIndex >= 0 && currentIndex < shapeList.size())) {
			return;
		}
		
		if ((event.getButton() == MouseEvent.BUTTON1) && (this.clickType == 1) ||
			(event.getButton() == MouseEvent.BUTTON3) && (this.clickType == 0)) {
			return;
		}
		
		ShapeInfo shape = shapeList.get(currentIndex);
		int x = event.getX();
		int y = event.getY();
		if (!shape.ContainPoint(x, y)) { // User clicked mouse at the outside of current circle.
			return;
		}
		
		
		synchronized(this) {
			currentIndex++;
			shape.setShootingX(x);
			shape.setShootingY(y);
			shape.setIsShooted(true);
			this.dataStateFlag = true;
			this.setCursor(defaultCursor);
		}
		this.repaint();
		// For Debuging
		String message = String.format("(%d, %d)", x, y);
		//JOptionPane.showMessageDialog(null, message);
		System.out.println(message);
		if (currentIndex == shapeList.size()) {
			JOptionPane.showConfirmDialog(this, "Game Over!", "Inform", JOptionPane.PLAIN_MESSAGE);
			return;
		}
	}
	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		if (isCalibration) {
			mouseClickedDuringCalibration(event);
		}
		else {
			mouseClickedDuringGame(event);
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isCalibration)
			return;
		if (currentIndex >= 0 && currentIndex < shapeList.size()) {
			ShapeInfo shape = shapeList.get(currentIndex);
			int X = arg0.getX();
			int Y = arg0.getY();
			if (shape.ContainPoint(X, Y) && this.getCursor() != this.crossCursor) {
				this.setCursor(this.crossCursor);
			}
			else if (!shape.ContainPoint(X, Y) && this.getCursor() != this.defaultCursor) {
				this.setCursor(this.defaultCursor);
			}
		}
		else {
			if (this.getCursor() != this.defaultCursor) {
				this.setCursor(this.defaultCursor);
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
