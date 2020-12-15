package com.zhou.baiduMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度坐标转墨卡托坐标测试类，单独拿出来测试的，与本程序无关
 * @author Lenovo
 *
 */
public class BDMercatorToLonLatAll {
    //以下是根据百度地图JavaScript API破解得到 百度坐标<->墨卡托坐标 转换算法
    private static double[] array1 = {75, 60, 45, 30, 15, 0};
    private static double[] array3 = {12890594.86, 8362377.87, 5591021, 3481989.83, 1678043.12, 0};
    private static double[][] array2 = {new double[]{-0.0015702102444, 111320.7020616939, 1704480524535203D, -10338987376042340D, 26112667856603880D, -35149669176653700D, 26595700718403920D, -10725012454188240D, 1800819912950474D, 82.5}
            , new double[]{0.0008277824516172526, 111320.7020463578, 647795574.6671607, -4082003173.641316, 10774905663.51142, -15171875531.51559, 12053065338.62167, -5124939663.577472, 913311935.9512032, 67.5}
            , new double[]{0.00337398766765, 111320.7020202162, 4481351.045890365, -23393751.19931662, 79682215.47186455, -115964993.2797253, 97236711.15602145, -43661946.33752821, 8477230.501135234, 52.5}
            , new double[]{0.00220636496208, 111320.7020209128, 51751.86112841131, 3796837.749470245, 992013.7397791013, -1221952.21711287, 1340652.697009075, -620943.6990984312, 144416.9293806241, 37.5}
            , new double[]{-0.0003441963504368392, 111320.7020576856, 278.2353980772752, 2485758.690035394, 6070.750963243378, 54821.18345352118, 9540.606633304236, -2710.55326746645, 1405.483844121726, 22.5}
            , new double[]{-0.0003218135878613132, 111320.7020701615, 0.00369383431289, 823725.6402795718, 0.46104986909093, 2351.343141331292, 1.58060784298199, 8.77738589078284, 0.37238884252424, 7.45}};
    private static double[][] array4 = {new double[]{1.410526172116255e-8, 0.00000898305509648872, -1.9939833816331, 200.9824383106796, -187.2403703815547, 91.6087516669843, -23.38765649603339, 2.57121317296198, -0.03801003308653, 17337981.2}
            , new double[]{-7.435856389565537e-9, 0.000008983055097726239, -0.78625201886289, 96.32687599759846, -1.85204757529826, -59.36935905485877, 47.40033549296737, -16.50741931063887, 2.28786674699375, 10260144.86}
            , new double[]{-3.030883460898826e-8, 0.00000898305509983578, 0.30071316287616, 59.74293618442277, 7.357984074871, -25.38371002664745, 13.45380521110908, -3.29883767235584, 0.32710905363475, 6856817.37}
            , new double[]{-1.981981304930552e-8, 0.000008983055099779535, 0.03278182852591, 40.31678527705744, 0.65659298677277, -4.44255534477492, 0.85341911805263, 0.12923347998204, -0.04625736007561, 4482777.06}
            , new double[]{3.09191371068437e-9, 0.000008983055096812155, 0.00006995724062, 23.10934304144901, -0.00023663490511, -0.6321817810242, -0.00663494467273, 0.03430082397953, -0.00466043876332, 2555164.4}
            , new double[]{2.890871144776878e-9, 0.000008983055095805407, -3.068298e-8, 7.47137025468032, -0.00000353937994, -0.02145144861037, -0.00001234426596, 0.00010322952773, -0.00000323890364, 826088.5}};

    public static Map<String, Double> convertLonLatToMC (Double lon, Double lat) {//百度坐标转墨卡托
        double[] arr = null;
        double n_lat = lat > 74 ? 74 : lat;
        n_lat = n_lat < -74 ? -74 : n_lat;
        for (int i = 0; i < array1.length; i++) {
            if (lat >= array1[i]) {
                arr = array2[i];
                break;
            }
        }
        if (arr == null) {
            for (int i = array1.length - 1; i >= 0; i--) {
                if (lat <= -array1[i]) {
                    arr = array2[i];
                    break;
                }
            }
        }
        return converter(lon, lat, arr);
    }

    public static Map<String, Double> convertMCToLonLat (Double x, Double y) {//墨卡托坐标转百度 - Mercator
        double[] arr = null;
        for (int i = 0; i < array3.length; i++) {
            if (Math.abs(y) >= array3[i]) {
                arr = array4[i];
                break;
            }
        }
        Map<String,Double> location = converter(Math.abs(x), Math.abs(y), arr);
        location.put("lng",location.get("x"));
        location.remove("x");
        location.put("lat",location.get("y"));
        location.remove("y");
        return location;
    }

    private static Map<String, Double> converter(double x, double y, double[] param) {
        Double T = param[0] + param[1] * Math.abs(x);
        Double cC = Math.abs(y) / param[9];
        Double cF = param[2] + param[3] * cC + param[4] * cC * cC + param[5] * cC * cC * cC + param[6] * cC * cC * cC * cC + param[7] * cC * cC * cC * cC * cC + param[8] * cC * cC * cC * cC * cC * cC;
        T *= (x < 0 ? -1 : 1);
        cF *= (y < 0 ? -1 : 1);
        Map<String, Double> location = new HashMap<String, Double>();
        location.put("x", T);
        location.put("y", cF);
        return location;
    }
    
    
    public static void main(String[] args) {
    	double lat = 32.077136;
        double lon = 118.801272;

        System.out.println("经纬度坐标x--->"+lon);
        System.out.println("经纬度坐标y--->"+lat);

        Map<String, Double> stringDoubleMap = BDMercatorToLonLatAll.convertLonLatToMC(lon, lat);
        //System.out.println("墨卡托坐标--->"+stringDoubleMap);

        Double xMC = stringDoubleMap.get("x");
        Double yMC = stringDoubleMap.get("y");
        System.out.println("墨卡托坐标x--->"+xMC);
        System.out.println("墨卡托坐标y--->"+yMC);

        //根据百度经纬度计算出来墨卡托坐标后，将结果除以地图分辨率Math.Pow(2,18-zoom)即可得到平面像素坐标，然后将像素坐标除以256分别得到瓦片的行列号。

        int zoom = 7;
        System.out.println("地图层级--->"+zoom);

        double dpi = Math.pow(2, 18 - zoom);
        System.out.println("地图分辨率--->"+dpi);

        double xPlanePixelCoordinates = xMC / dpi;
        double yPlanePixelCoordinates = yMC / dpi;
        System.out.println("平面像素坐标x--->"+xPlanePixelCoordinates);
        System.out.println("平面像素坐标y--->"+yPlanePixelCoordinates);

        int tileImgDpi = 256;
        System.out.println("瓦片大小--->"+tileImgDpi);

        double xTile = xPlanePixelCoordinates / tileImgDpi;
        double yTile = yPlanePixelCoordinates / tileImgDpi;
        //System.out.println("瓦片行列号x--->"+xTile);
        //System.out.println("瓦片行列号y--->"+yTile);

        int xTileI = (int) xTile;
        int yTileI = (int) yTile;
        System.out.println("瓦片行列号x--->"+xTileI);
        System.out.println("瓦片行列号y--->"+yTileI);
	}
}
