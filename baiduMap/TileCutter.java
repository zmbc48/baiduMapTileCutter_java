package com.zhou.baiduMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileCutter {
	
	

	private String imgPath;
	private String outputPath;
	private OutputFileTypes outputFileType;
	private LatLng center;
	private ZoomInfo zoomInfo;
	private OutputLayerTypes outputLayerType;
	private String mapTypeName;

	private BufferedImage tileImage;
	private int imgWidth;
	private int imgHeight;

	private int totalZoom = 0;
	private int totalCount = 0;
	private int finishCount = 0;

	public TileCutter() {

	}

	public int GetFinishCount() {
		return finishCount;
	}

	public int GetTotalCount() {
		return totalCount;
	}

	public void SetInfo(String imgPath, String outputPath, OutputFileTypes outputFileType, 
			LatLng center, ZoomInfo zoomInfo, OutputLayerTypes outputLayerType, String mapTypeName) throws IOException {
		this.imgPath = imgPath;
		this.outputPath = outputPath;
		this.outputFileType = outputFileType;
		this.center = center;
		this.zoomInfo = zoomInfo;
		this.outputLayerType = outputLayerType;
		this.mapTypeName = mapTypeName;

		tileImage = ImageIO.read(new File(imgPath));
		imgWidth = tileImage.getWidth();
		imgHeight = tileImage.getHeight();
	}

	public void StartCut() {
		//一共有几层
		totalZoom = zoomInfo.getMaxZoom() - zoomInfo.getMinZoom() + 1;
		//一共需要切割多少张图片
		totalCount = 0;
		for (int i = zoomInfo.getMinZoom(); i <= zoomInfo.getMaxZoom(); i++) {
			totalCount += calcTotalTilesByZoom(i);
		}
		//开始切割图片
		beginCut();
		
		//切割完成后生成html代码
		if (outputFileType.getOutputFileType() == OutputFileTypes.TileAndCode) {
			generateHTMLFile();
		}
		
	}

	

	private int calcTotalTilesByZoom(int zoom) {
		// 计算中心点所在的网格编号
		// 中心点的墨卡托平面坐标
		Point mcPoint = Projection.fromLatLngToPoint(center);
		// 中心点在整个世界中的像素坐标
		Point pxPoint = mcPoint.Multiply(Math.pow(2, zoom - 18));
		pxPoint.Round();
		Point tileCoord = pxPoint.Divide(256);
		tileCoord.Floor();

		// 中心网格左下角的像素坐标（相对于整个世界的像素坐标）
		// Point centerTilePixel = tileCoord.Multiply(256);

		int imgWidthForCurrentZoom = (int) Math.round(imgWidth * Math.pow(2, zoom - zoomInfo.getImageZoom()));
		int imgHeightForCurrentZoom = (int) Math.round(imgHeight * Math.pow(2, zoom - zoomInfo.getImageZoom()));

		// 图片左下角的像素坐标和网格编号
		Point bottomLeftPixel = new Point(pxPoint.getX() - imgWidthForCurrentZoom / 2, pxPoint.getY() - imgHeightForCurrentZoom / 2);
		bottomLeftPixel.Round();
		Point bottomLeftTileCoords = bottomLeftPixel.Divide(256);
		bottomLeftTileCoords.Floor();
		// 图片右上角的像素坐标和网格编号
		Point upperRightPixel = new Point(pxPoint.getX() + imgWidthForCurrentZoom / 2, pxPoint.getY() + imgHeightForCurrentZoom / 2);
		upperRightPixel.Floor();
		Point upperRightTileCoords = upperRightPixel.Divide(256);
		upperRightTileCoords.Floor();

		int totalCols = (int) (upperRightTileCoords.getX() - bottomLeftTileCoords.getX() + 1);
		int totalRows = (int) (upperRightTileCoords.getY() - bottomLeftTileCoords.getY() + 1);

		return totalCols * totalRows;
	}

	private void generateHTMLFile() {
		BufferedWriter htmlFile = null;
		try {
			htmlFile = new BufferedWriter(new FileWriter(outputPath + "/index.html"));
			//StreamWriter htmlFile = File.CreateText(outputPath + "/index.html");
			htmlFile.write("<!DOCTYPE html>\t\n");
			htmlFile.write("<html>\t\n");
			htmlFile.write("<head>\t\n");
			htmlFile.write("<meta charset=\"utf-8\">\t\n");
			htmlFile.write("<title>自定义地图类型</title>\t\n");
			htmlFile.write("<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=1.2\"></script>\t\n");
			htmlFile.write("</head>\t\n");
			htmlFile.write("<body>\t\n");
			htmlFile.write("<div id=\"map\" style=\"width:1000px;height:640px\"></div>\t\n");
			htmlFile.write("<script type=\"text/javascript\">\t\n");
			htmlFile.write("var tileLayer = new BMap.TileLayer();\t\n");
			htmlFile.write("tileLayer.getTilesUrl = function(tileCoord, zoom) {\t\n");
			htmlFile.write("    var x = tileCoord.x;\t\n");
			htmlFile.write("    var y = tileCoord.y;\t\n");
			htmlFile.write("    return 'tiles/' + zoom + '/tile-' + x + '_' + y + '.png';\t\n");
			htmlFile.write("}\t\n");
			if (outputLayerType.outputLayerType == OutputLayerTypes.MapType) {
				htmlFile.write("var " + mapTypeName + " = new BMap.MapType('" + mapTypeName + "', tileLayer, {minZoom: " + zoomInfo.getMinZoom() + ", maxZoom: " + zoomInfo.getMaxZoom()
						+ "});\t\n");
				htmlFile.write("var map = new BMap.Map('map', {mapType: " + mapTypeName + "});\t\n");
			} else if (outputLayerType.outputLayerType == OutputLayerTypes.NormalLayer) {
				htmlFile.write("var map = new BMap.Map('map');\t\n");
				htmlFile.write("map.addTileLayer(tileLayer);\t\n");
			}
			htmlFile.write("map.addControl(new BMap.NavigationControl());\t\n");
			htmlFile.write("map.centerAndZoom(new BMap.Point(" + center.toString() + "), " + zoomInfo.getImageZoom() + ");\t\n");
			htmlFile.write("</script>\t\n");
			htmlFile.write("</body>\t\n");
			htmlFile.write("</html>\t\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != htmlFile){
				try {
					htmlFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        
		
	}

	private void beginCut() {
		Bitmap bitmap = new Bitmap(null);
		for (int i = zoomInfo.getMinZoom(); i <= zoomInfo.getMaxZoom(); i++) {
			BufferedImage image;
			if (i == zoomInfo.getImageZoom()) {
				image = tileImage;
			} else {
				// 生成临时图片
				Size newSize = new Size();
				double factor = Math.pow(2, i - zoomInfo.getImageZoom());
				newSize.setWidth( (int) Math.round(imgWidth * factor));
				newSize.setHeight( (int) Math.round(imgHeight * factor));
				if (newSize.getWidth() < 256 || newSize.getHeight() < 256) {
					// 图片尺寸过小不再切了
					System.out.println("尺寸过小，跳过");
					continue;
				}
				
				image = bitmap.copyImage(tileImage, newSize.getWidth(), newSize.getHeight());
			}
			cutImage(image, i);
		}
		
		if (finishCount != totalCount) {
			System.out.println("修正完成的网格数");
			finishCount = totalCount;
		}
	}

	// / <summary>
	// / 切某一级别的图
	// / </summary>
	// / <param name="imgFile">图片对象</param>
	// / <param name="zoom">图片对应的级别</param>
	private void cutImage(BufferedImage  imgFile, int zoom) {
		int halfWidth = (int) Math.round((double) imgFile.getWidth() / 2);
		int halfHeight = (int) Math.round((double) imgFile.getHeight() / 2);
		//Directory.CreateDirectory(outputPath + "/tiles/" + zoom);

		// 计算中心点所在的网格编号
		// 中心点的墨卡托平面坐标
		Point mcPoint = Projection.fromLatLngToPoint(center);
		// 中心点在整个世界中的像素坐标
		Point pxPoint = mcPoint.Multiply(Math.pow(2, zoom - 18));
		pxPoint.Round();
		Size pxDiff = new Size(0 - (int) pxPoint.getX(), 0 - (int) pxPoint.getY());
		Point tileCoord = pxPoint.Divide(256);
		tileCoord.Floor();

		

		// 图片左下角的像素坐标和网格编号
		Point bottomLeftPixel = new Point(pxPoint.getX() - halfWidth, pxPoint.getY() - halfHeight);
		bottomLeftPixel.Round();
		Point bottomLeftTileCoords = bottomLeftPixel.Divide(256);
		bottomLeftTileCoords.Floor();
		// 图片右上角的像素坐标和网格编号
		Point upperRightPixel = new Point(pxPoint.getX() + halfWidth, pxPoint.getY() + halfHeight);
		upperRightPixel.Floor();
		Point upperRightTileCoords = upperRightPixel.Divide(256);
		upperRightTileCoords.Floor();

		int totalCols = (int) (upperRightTileCoords.getX() - bottomLeftTileCoords.getX() + 1);
		int totalRows = (int) (upperRightTileCoords.getY() - bottomLeftTileCoords.getY() + 1);
		System.out.println("total col and row: " + totalCols + ", " + totalRows);
		for (int i = 0; i < totalCols; i++) {
			for (int j = 0; j < totalRows; j++) {
				Bitmap img = new Bitmap(256, 256);
				
				Point eachTileCoords = new Point(bottomLeftTileCoords.getX() + i, bottomLeftTileCoords.getY() + j);
				int offsetX = (int) eachTileCoords.getX() * 256 + pxDiff.getWidth() + halfWidth;
				int offsetY = halfHeight - ((int) eachTileCoords.getY() * 256 + pxDiff.getHeight()) - 256;
				
				try {
					BufferedImage targetImg =  img.copyImagePixel(imgFile, offsetX, offsetY);
					String imgFileName = outputPath + "/tiles/" + zoom + "/tile-" + new Double(eachTileCoords.getX()).intValue() + "_" + new Double( eachTileCoords.getY()).intValue() + ".png";
					
					//画矩形
					Graphics2D g = targetImg.createGraphics();
					g.setColor(Color.RED);
					g.drawRect(0, 0, 255,255 );
					g.dispose();
					
					
					img.save(targetImg,imgFileName,"png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				finishCount++;
			}
		}
		
	}

	
	
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TileCutter ti =new TileCutter();
		String imgPath = "d:\\0.jpg";
		OutputLayerTypes outputLayerType = new OutputLayerTypes(1);
		OutputFileTypes outputFileType =new OutputFileTypes(1);
		String outputPath = "d:\\work";
		LatLng center = new LatLng(0,0);
		
		ZoomInfo zoomInfo = new ZoomInfo(4, 4, 4);
		String mapTypeName="自己切图";
		ti.SetInfo(imgPath, outputPath, outputFileType, center, zoomInfo, outputLayerType, mapTypeName);
		ti.StartCut();
	}
}
