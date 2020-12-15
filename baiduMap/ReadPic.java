package com.zhou.baiduMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class ReadPic {
	
	public static void main(String[] args) throws IOException {
		
		
		String dir = "D:\\work\\tiles";
		ReadPic r = new ReadPic();
		List<String> filePath = new ArrayList<>();
		r.getFiles(dir,filePath);
		for(int i =0;i<filePath.size();i++){
			String srcImage = filePath.get(i);
			String[] names = srcImage.split("\\\\");
			String outImage = names[0]+"/"+names[1]+"/"+names[2]+"/"+names[3]+"/"+names[4];
			//String str = r.getPath(filePath.get(i));
			//String outImage = dir +"/"+str.split(",")[0]+"/"+str.split(",")[1]+"/"+str.split(",")[2]+".png";
			//System.out.println(outImage);
			String[] temp= names[4].split("_");
			String str = names[3]+","+temp[0].replace("tile", "")+","+temp[1].replace(".png", "");
			drawOnePic(new File(srcImage), new File(outImage), str);
		}
		
	}


	
	private  String getPath(String name) {
		String[] names = name.split("\\\\");
		Pattern pattern = Pattern.compile("tile");
		Matcher matcher = pattern.matcher(names[3]);
		matcher.find();
		
		
		Pattern pattern_ =Pattern.compile("_");
		Matcher matcher2 = pattern_.matcher(names[3]);
		matcher2.find();
		
		
		Pattern patternPoint = Pattern.compile(".png");
		Matcher matcher3 = patternPoint.matcher(names[3]);
		matcher3.find();
		
		
		String a = names[3].substring(matcher.end(), matcher2.start());
		String b = names[3].substring(matcher2.end(),matcher3.start());
		return names[2]+","+a+","+b;
		
	}
	
	
	/**
	    * @Author：
	    * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取
	    * @Date：
	    */
	    public void getFiles(String path,List<String> filePath) {
	      
	        File file = new File(path);
	        if(file.isDirectory()){
	        	File[] tempList = file.listFiles();
	        	for (int i = 0; i < tempList.length; i++) {
	        		String name = tempList[i].getName();
	 	            if (tempList[i].isDirectory()) {
	 	                //这里就不递归了，
	 	            	//System.out.println(tempList[i].getAbsolutePath());
	 	            	getFiles(tempList[i].getAbsolutePath(),filePath);
	 	            }else{
	 	            	if(name.contains("png")){
	 	            		System.out.println(tempList[i].getAbsolutePath());
	 	            		filePath.add(tempList[i].getAbsolutePath());
	 	            	}
	 	            }
	 	        }
	        }else{
	              System.out.println( file.getName());
	        }
	    }

	private static void drawOnePic(File srcImageFile, File outImageFile, String str) throws IOException {
		BufferedImage bi = ImageIO.read(srcImageFile);
	    Graphics2D g = bi.createGraphics();
	    g.setColor(Color.RED);
	    g.drawRect(0, 0, 256,256 );
	    g.setBackground(Color.WHITE);
	    g.drawImage(bi, 0, 0, null);
	    Font font = new Font("Arial", Font.PLAIN, 20);
	    g.setFont(font);
	    g.drawString(str, 100, 20);
        g.drawImage(bi, 0, 0, null);
	    g.dispose();
		ImageIO.write(bi, "png", outImageFile);
	}
	
	
	
	 

}
