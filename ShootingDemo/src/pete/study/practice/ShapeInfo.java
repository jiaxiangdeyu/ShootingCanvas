/**
 * 
 */
package pete.study.practice;

import java.awt.Graphics;

/**
 * @author Wenping
 *
 */
public class ShapeInfo {
	protected int m_length;
	protected int m_X;
	protected int m_Y;
	
	protected int m_shootingX;
	protected int m_shootingY;
	protected boolean m_isShooted;

	public int getLength() {
		return m_length;
	}
	public void setLength(int length) {
		this.m_length = length;
	}
	public int getX() {
		return m_X;
	}
	public void setX(int X) {
		this.m_X = X;
	}
	public int getY() {
		return m_Y;
	}
	public void setY(int Y) {
		this.m_Y = Y;
	}
	public int getShootingX() {
		return m_shootingX;
	}
	public void setShootingX(int shootingX) {
		this.m_shootingX = shootingX;
	}
	public int getShootingY() {
		return m_shootingY;
	}
	public void setShootingY(int shootingY) {
		this.m_shootingY = shootingY;
	}
	public boolean getIsShooted() {
		return m_isShooted;
	}
	public void setIsShooted(boolean isShooted) {
		this.m_isShooted = isShooted;
	}
	
	public ShapeInfo() {
		m_length = 50;
		m_X = 100;
		m_Y = 100;
		m_shootingX = -1;
		m_shootingY = -1;
		m_isShooted = false;
	}
	public ShapeInfo(int length, int X, int Y) {
		m_length = length;
		m_X = X;
		m_Y = Y;
		m_shootingX = -1;
		m_shootingY = -1;
		m_isShooted = false;		
	}
	public String getShapeType() {
		return "Shape";
	}
	public String getHeadLine() {
		return "Length, X, Y, ShootingX, ShootingY";
	}
	public String toString() {
		StringBuilder objectStr = new StringBuilder();
		objectStr.append(m_length);
		objectStr.append(", ");
		objectStr.append(m_X);
		objectStr.append(", ");
		objectStr.append(m_Y);
		objectStr.append(", ");
		objectStr.append(m_shootingX);
		objectStr.append(", ");
		objectStr.append(m_shootingY);
		return objectStr.toString();
	}
	public String toString(Double matchError) {
		StringBuilder objectStr = new StringBuilder();
		objectStr.append(m_length);
		objectStr.append(", ");
		objectStr.append(m_X);
		objectStr.append(", ");
		objectStr.append(m_Y);
		objectStr.append(", ");
		objectStr.append(m_shootingX);
		objectStr.append(", ");
		objectStr.append(m_shootingY);
		return objectStr.toString();
	}
	public void drawShape(Graphics g) {
		// For debuging.
		System.out.println("Draw Shape.");
		return;
	}
	public boolean ContainPoint(int pointX, int pointY) {
		return false;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShapeInfo c1 = new ShapeInfo();
		System.out.println(c1.toString());
		ShapeInfo c2 = new ShapeInfo(10, 30, 40);
		System.out.println(c2.toString());
	}

}
