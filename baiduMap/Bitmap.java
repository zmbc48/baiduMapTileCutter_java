package com.zhou.baiduMap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 本来是要完善该类的，后来程序写完了，没有把该类整完
 * @author Lenovo
 *
 */
public class Bitmap {

	private String imgPath;//文件路径

	private int height; //图片的高度
	private int width;//图片的宽度

	private Size size;//图片大小

	public Bitmap(String imgPath) {
	}

	public Bitmap(Bitmap tileImage, Size newSize) {
		// TODO Auto-generated constructor stub
	}

	public Bitmap(int width, int height) {
		// TODO Auto-generated constructor stub
	}

	public void Dispose() {
		// TODO Auto-generated method stub

	}

	

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath
	 *            the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the size
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	public Color GetPixel(int pixelX, int pixelY) {
		// TODO Auto-generated method stub
		return null;
	}

	public void SetPixel(int i, int j, Color color) {
		// TODO Auto-generated method stub

	}


	/**
	 * 切图的一个小例子，程序中没有用到该方法
	 * @param sourceImg
	 * @param newWidth
	 * @param newHeight
	 * @throws IOException
	 */
	private void cutPic(BufferedImage sourceImg, int newWidth, int newHeight) throws IOException {
		// 建一个256x256空白图片
		BufferedImage bigImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		// 获取空白图片的画笔
		Graphics g = bigImg.getGraphics();
		// 把子图片画到空白图片里面
		g.drawImage(sourceImg, (newWidth - sourceImg.getWidth()) / 2, (newHeight - sourceImg.getHeight()) / 2, null);
		g.dispose();

		int col = newWidth / 256;
		int row = newHeight / 256;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				BufferedImage targetImg = bigImg.getSubimage(j * 256, i * 256, 256, 256);
				File outFile = new File("d:\\work\\" + i + "_" + j + ".png");
				ImageIO.write(targetImg, "png", outFile);
			}
		}
	}

	
	/**
	 * 保存图片
	 * @param targetImg    源图片
	 * @param imgFileName  要保存图片的路径
	 * @param string       保存的格式
	 * @throws IOException
	 */
	public void save(BufferedImage targetImg, String imgFileName, String string) throws IOException {
		File outFile = new File(imgFileName);
		outFile.mkdirs();
		outFile.createNewFile();
		ImageIO.write(targetImg, string, outFile);
	}
	
	/**
	 * 拷贝图片
	 * @param sourceImg 源图片
	 * @param outWidth  目标图片的宽度
	 * @param outHeight  目标图片的高度
	 * @return
	 */
	public BufferedImage copyImage(BufferedImage sourceImg, int outWidth, int outHeight) {
		BufferedImage changeImage = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = changeImage.createGraphics();
		g2d.drawImage(sourceImg, 0, 0, outWidth, outHeight, null);
		return changeImage;
	}
	
	/**
	 * 根据像素拷贝图片
	 * @param imgFile  源图片
	 * @param offsetX  x坐标偏移量
	 * @param offsetY  y坐标偏移量
	 * @return
	 */
	public BufferedImage copyImagePixel(BufferedImage imgFile, int offsetX, int offsetY) {
		//目标图片的大小是256x256大小，默认透明的。
		BufferedImage descImg = new BufferedImage(256, 256, BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				// 默认透明色
				Color col = new Color(0, 0, 0, 0);
				int rgb = col.getRGB();
				int pixelX = offsetX + i;
				int pixelY = offsetY + j;
				if (pixelX >= 0 && pixelX < imgFile.getWidth() && pixelY >= 0 && pixelY < imgFile.getHeight()) {
					rgb = imgFile.getRGB(pixelX, pixelY);
				}
				descImg.setRGB(i, j, rgb);
			}
		}
		return descImg;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		String imagePath = "D:\\0.jpg";
		BufferedImage sourceImg = ImageIO.read(new File(imagePath));
		BufferedImage descImg = new BufferedImage(sourceImg.getWidth(), sourceImg.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < sourceImg.getWidth(); i++) {
			for (int j = 0; j < sourceImg.getHeight(); j++) {
				int rgb = sourceImg.getRGB(i, j);
				descImg.setRGB(i, j, rgb);
			}
		}
		String outPath = "d:\\work\\ab.jpg";
		File outFile = new File(outPath);
		ImageIO.write(descImg, "jpg", outFile);

	}

}
