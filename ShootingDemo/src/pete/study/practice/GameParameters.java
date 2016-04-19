package pete.study.practice;

public class GameParameters {
	private boolean isRandomInGroup;
	private int clickTypeFlag;
	private int testTypeFlag;
	private int shapeFlag;
	private int lowParameter, upParameter, numParameter;

	public boolean getIsRandomInGroup() {
		return isRandomInGroup;
	}
	public void setIsRandomInGroup(boolean isRandomInGroup) {
		this.isRandomInGroup = isRandomInGroup;
	}
	/**
	 * @return the clickTypeFlag
	 */
	public int getClickTypeFlag() {
		return clickTypeFlag;
	}
	/**
	 * @param clickTypeFlag the clickTypeFlag to set
	 */
	public void setClickTypeFlag(int clickTypeFlag) {
		this.clickTypeFlag = clickTypeFlag;
	}
	public int getTestTypeFlag() {
		return testTypeFlag;
	}
	public void setTestTypeFlag(int testTypeFlag) {
		this.testTypeFlag = testTypeFlag;
	}
	public int getShapeFlag() {
		return shapeFlag;
	}
	public void setShapeFlag(int shapeFlag) {
		this.shapeFlag = shapeFlag;
	}
	public int getLowParameter() {
		return lowParameter;
	}
	public void setLowParameter(int lowParameter) {
		this.lowParameter = lowParameter;
	}
	public int getUpParameter() {
		return upParameter;
	}
	public void setUpParameter(int upParameter) {
		this.upParameter = upParameter;
	}
	public int getNumParameter() {
		return numParameter;
	}
	public void setNumParameter(int numParameter) {
		this.numParameter = numParameter;
	}
	public void init() {
		isRandomInGroup = true;
		testTypeFlag = 0; // 0 No restriction, 1 to left, 2 To Right, 3 Stay Center 
		clickTypeFlag = 0; // 0 Left button, 1 Right Button
		shapeFlag = 1; // 0 Line, 1 Circle
		lowParameter = 20;
		upParameter = 50;
		numParameter = 25;
	}
	public void setParameters(boolean isRandomInGroup, int clickTypeFlag, int testTypeFlag, int shapeFlag, int lowPara, int upPara, int numPara) {
		this.isRandomInGroup = isRandomInGroup;
		this.clickTypeFlag = clickTypeFlag;
		this.testTypeFlag = testTypeFlag;
		this.shapeFlag = shapeFlag;
		this.lowParameter = lowPara;
		this.upParameter = upPara;
		this.numParameter = numPara;
	}
	public void setParameters(boolean isRandomInGroup, int testTypeFlag, int shapeFlag, int lowPara, int upPara, int numPara) {
		this.isRandomInGroup = isRandomInGroup;
		this.testTypeFlag = testTypeFlag;
		this.shapeFlag = shapeFlag;
		this.lowParameter = lowPara;
		this.upParameter = upPara;
		this.numParameter = numPara;
	}
}
