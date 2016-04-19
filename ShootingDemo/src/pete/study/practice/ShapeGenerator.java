package pete.study.practice;

import java.util.Random;
import java.util.Vector;

public class ShapeGenerator {
	private boolean isRandomInGroup;
	private int shapeFlag; // 0 Line, 1 Circle
	private int testTypeFlag; // " 0 no restriction", "1 To Left", "2 To Right", "3 Stay Center"
	private int lowBound;
	private int upBound;
	private int totalNum;
	
	private Vector<ShapeInfo> shapeList;
	
	private int width;
	private int height;

	public ShapeGenerator(GameParameters para, int width, int height) {
		double dpi = ConfigParameters.getScreenDPI();
		this.isRandomInGroup = para.getIsRandomInGroup();
		this.testTypeFlag = para.getTestTypeFlag();
		this.shapeFlag = para.getShapeFlag();
		// Transform mm to pixel
		double lowBoundInMM = para.getLowParameter();
		this.lowBound = (int) ((lowBoundInMM / 10.0) * (dpi / 2.54));
		if (this.lowBound > width)
			this.lowBound = width;
		double upBoundInMM = para.getUpParameter();
		this.upBound = (int) ((upBoundInMM / 10.0) * (dpi / 2.54));
		if (this.upBound > width)
			this.upBound = width;
		this.totalNum = para.getNumParameter();
		this.width = width;
		this.height = height;
		this.shapeList = new Vector<ShapeInfo>();
	}
	public void generateShape() {
		Random rand = new Random();
		int length = lowBound;
		if (upBound > lowBound)
			length = rand.nextInt(Math.abs(upBound - lowBound)) + lowBound;
		
		int i = 0;
		if (this.shapeFlag == 0) {  // Line
			while (i < totalNum) {
				// Random in group.
				if (this.isRandomInGroup && upBound > lowBound) {
					length = rand.nextInt(Math.abs(upBound - lowBound)) + lowBound;
				}
				int startX = 0;
				switch(this.testTypeFlag) {
				case 0: // No restriction
					startX = rand.nextInt(Math.abs(width - length));
					break;
				case 1: // To Left
					startX = rand.nextInt(Math.abs(width - length) / 2);
					break;
				case 2: // To Right
					startX = rand.nextInt(Math.abs(width - length) / 2) + Math.abs(width - length) / 2 + 1;
				    break;
				case 3: // Stay Center
					startX = Math.abs(width - length) / 2;
					break;
				default: // No restriction
					startX = rand.nextInt(Math.abs(width - length));
				}
				// We do not want to locate this line at the top and bottom bounder around.
				int startY = rand.nextInt(Math.abs(height - 2 * 10)) + 10;
				LineInfo line = new LineInfo(length, startX, startY);
				shapeList.add(line);
				i++;
			}
		}
		else {
			while(i < totalNum) {
				// Random in group.
				if (this.isRandomInGroup && upBound > lowBound) {
					length = rand.nextInt(Math.abs(upBound - lowBound)) + lowBound;
				}
				int centerX = 0;
				switch(this.testTypeFlag) {
				case 0: // No restriction
					centerX = rand.nextInt(Math.abs(width - 2 * length)) + length;
					break;
				case 1: // To Left
					centerX = rand.nextInt(Math.abs(width / 2 - length)) + length;
					break;
				case 2: // To Right
					centerX = rand.nextInt(Math.abs(width / 2 - length)) + width / 2 + 1;
				    break;
				case 3: // Stay Center
					centerX = width / 2;
					break;
				default: // No restriction
					centerX = rand.nextInt(Math.abs(width - length));
				}
				int centerY = rand.nextInt(Math.abs(height - 2 * length)) + length;
				CircleInfo circle = new CircleInfo(length, centerX, centerY);
				shapeList.add(circle);
				i++;
			}
		}
	}

	public Vector<ShapeInfo> getShapeList() {
		return this.shapeList;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
