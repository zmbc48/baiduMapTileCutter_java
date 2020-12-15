package com.zhou.baiduMap;

public class OutputFileTypes {
	
	public int outputFileType;

	//输出文件类型
	public static final  int TileAndCode = 1;
	
	public static final int TileOnly = 2;

	/**
	 * @return the outputFileType
	 */
	public int getOutputFileType() {
		return outputFileType;
	}

	/**
	 * @param outputFileType the outputFileType to set
	 */
	public void setOutputFileType(int outputFileType) {
		this.outputFileType = outputFileType;
	}

	public OutputFileTypes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OutputFileTypes(int outputFileType) {
		super();
		this.outputFileType = outputFileType;
	}
	
	
	
	
	

}
