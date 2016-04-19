package pete.study.practice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class CircleInfo extends ShapeInfo {
	public CircleInfo(int radius, int centerX, int centerY) {
		super(radius, centerX, centerY);
	}
	public String getShapeType() {
		return "Circle";
	}
	
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#getHeadLine()
	 */
	@Override
	public String getHeadLine() {
		return "Radius, CenterX, CenterY, ShootingX, ShootingY, RelativeDirection";
	}
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#toString()
	 */
	@Override
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
		objectStr.append(", ");
		if (m_shootingX < 0 && m_shootingY < 0) {
			objectStr.append("noshooting");
		}
		else if (m_shootingX < m_X) {
			if (m_shootingY < m_Y) {
				objectStr.append("upperleft");
			}
			else if (m_shootingY > m_Y) {
				objectStr.append("lowerleft");
			}
			else {
				objectStr.append("left");
			}
		}
		else if (m_shootingX > m_X) {
			if (m_shootingY < m_Y) {
				objectStr.append("upperright");
			}
			else if (m_shootingY > m_Y) {
				objectStr.append("lowerright");
			}
			else {
				objectStr.append("right");
			}
		}
		else {
			if (m_shootingY < m_Y) {
				objectStr.append("upper");
			}
			else if (m_shootingY > m_Y) {
				objectStr.append("lower");
			}
			else {
				objectStr.append("match");
			}
		}				
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
		objectStr.append(", ");
		if (m_shootingX < 0 && m_shootingY < 0) {
			objectStr.append("noshooting");
		}
		else if (Math.sqrt( Math.pow(m_shootingX - m_X, 2) + Math.pow(m_shootingY - m_Y, 2)) <= matchError) {
			objectStr.append("match");
		}
		else if (m_shootingX < m_X) {
			if (m_shootingY < m_Y) {
				objectStr.append("upperleft");
			}
			else if (m_shootingY > m_Y) {
				objectStr.append("lowerleft");
			}
			else {
				objectStr.append("left");
			}
		}
		else if (m_shootingX > m_X) {
			if (m_shootingY < m_Y) {
				objectStr.append("upperright");
			}
			else if (m_shootingY > m_Y) {
				objectStr.append("lowerright");
			}
			else {
				objectStr.append("right");
			}
		}
		else { // m_shootingX == m_X
			if (m_shootingY < m_Y) {
				objectStr.append("upper");
			}
			else if (m_shootingY > m_Y) {
				objectStr.append("lower");
			}
		}				
		return objectStr.toString();
	}
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#drawShape(java.awt.Graphics)
	 */
	@Override
	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(2.0f));
		
		int radius = this.getLength();
		int leftUpperX = this.getX() - radius;
		int leftUpperY = this.getY() - radius;
		Ellipse2D ellipse = new Ellipse2D.Double(leftUpperX, leftUpperY, radius * 2, radius *2);
		g2d.draw(ellipse);
	}
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#ContainPoint(int, int)
	 */
	@Override
	public boolean ContainPoint(int pointX, int pointY) {
		int radius = this.getLength();
		int centerX = this.getX();
		int centerY = this.getY();
		double dist = Math.sqrt((centerX - pointX)*(centerX - pointX) + (centerY - pointY) * (centerY - pointY));
		return dist <= radius;
	}
	
}
