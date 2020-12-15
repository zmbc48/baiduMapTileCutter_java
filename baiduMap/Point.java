package com.zhou.baiduMap;

public class Point {
	public double x = 0;
	public double y = 0;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	public Point Multiply(double factor) {
		return new Point(this.x * factor, this.y * factor);
	}

	public Point Divide(double factor) {
		return new Point(this.x / factor, this.y / factor);
	}

	public void Round() {
		this.x = Math.round(this.x);
		this.y = Math.round(this.y);
	}

	public void Floor() {
		this.x = Math.floor(this.x);
		this.y = Math.floor(this.y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

}
