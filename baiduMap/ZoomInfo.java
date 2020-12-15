package com.zhou.baiduMap;

public class ZoomInfo {
	
	public int minZoom;
	public int maxZoom;
	public int imageZoom;
	/**
	 * @return the minZoom
	 */
	public int getMinZoom() {
		return minZoom;
	}
	/**
	 * @param minZoom the minZoom to set
	 */
	public void setMinZoom(int minZoom) {
		this.minZoom = minZoom;
	}
	/**
	 * @return the maxZoom
	 */
	public int getMaxZoom() {
		return maxZoom;
	}
	/**
	 * @param maxZoom the maxZoom to set
	 */
	public void setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
	}
	/**
	 * @return the imageZoom
	 */
	public int getImageZoom() {
		return imageZoom;
	}
	/**
	 * @param imageZoom the imageZoom to set
	 */
	public void setImageZoom(int imageZoom) {
		this.imageZoom = imageZoom;
	}
	public ZoomInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param minZoom 最小
	 * @param maxZoom 最大
	 * @param imageZoom  本土片在哪个层级
	 */
	public ZoomInfo(int minZoom, int maxZoom, int imageZoom) {
		super();
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.imageZoom = imageZoom;
	}
	
	
	

}
