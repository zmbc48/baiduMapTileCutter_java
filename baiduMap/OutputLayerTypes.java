package com.zhou.baiduMap;

public class OutputLayerTypes {

	public int outputLayerType;

	// 独立地图类型，还是叠加图层
	public static final int MapType = 1;// 独立地图

	public static final int NormalLayer = 2;// 叠加图层(图层后面有地图)

	public OutputLayerTypes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the outputLayerType
	 */
	public int getOutputLayerType() {
		return outputLayerType;
	}

	/**
	 * @param outputLayerType
	 *            the outputLayerType to set
	 */
	public void setOutputLayerType(int outputLayerType) {
		this.outputLayerType = outputLayerType;
	}

	public OutputLayerTypes(int outputLayerType) {
		super();
		this.outputLayerType = outputLayerType;
	}

}
