package pete.study.practice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class LineInfo extends ShapeInfo {
	public LineInfo(int length, int startX, int startY) {
		super(length, startX, startY);
	}
	public String getShapeType() {
		return "Line";
	}
	
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#getHeadLine()
	 */
	@Override
	public String getHeadLine() {
		return "Length, MiddleX, MiddleY, ShootingX, ShootingY, RelativeDirection";
	}
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#toString()
	 */
	@Override
	public String toString() {
		int middleX = m_X + m_length / 2;
		StringBuilder objectStr = new StringBuilder();
		objectStr.append(m_length);
		objectStr.append(", ");
		objectStr.append(middleX);
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
		else if (m_shootingX < middleX) {
			objectStr.append("left");
		}
		else if (m_shootingX > middleX) {
			objectStr.append("right");
		}
		else {
			objectStr.append("match");
		}				
		return objectStr.toString();
	}
	public String toString(Double matchError) {
		int middleX = m_X + m_length / 2;
		StringBuilder objectStr = new StringBuilder();
		objectStr.append(m_length);
		objectStr.append(", ");
		objectStr.append(middleX);
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
		else if (Math.abs(m_shootingX - middleX) <= matchError) {
			objectStr.append("match");
		}
		else if (m_shootingX < middleX) {
			objectStr.append("left");
		}
		else if (m_shootingX > middleX) {
			objectStr.append("right");
		}				
		return objectStr.toString();
	}
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#drawShape(java.awt.Graphics)
	 */
	@Override
	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(2.0f));
		
		int length = this.getLength();
		int startX = this.getX();
		int startY = this.getY();
		int endX = startX + length;
		int endY = startY;
		Line2D line = new Line2D.Double(startX, startY, endX, endY);
		g2d.draw(line);
	}
	/* (non-Javadoc)
	 * @see pete.study.practice.ShapeInfo#ContainPoint(int, int)
	 */
	@Override
	public boolean ContainPoint(int pointX, int pointY) {
		// we define one point locates at this line.
		int length = this.getLength();
		int startX = this.getX();
		int startY = this.getY();
		return Math.abs(startY - pointY) <= 3 && (pointX >= startX && pointX <= startX + length);
	}
	
}
