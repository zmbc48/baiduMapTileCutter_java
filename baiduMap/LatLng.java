package com.zhou.baiduMap;

public class LatLng {
	 public double lat = 0;
     public double lng = 0;
     
     
     
	public LatLng() {
		super();
	}
	
	
	public LatLng(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}


	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		double value = lat;
		  if (value < -90)
          {
              value = -90;
          } else if (value > 90)
          {
              value = 90;
          }
          this.lat = value;
	}
	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		double value = lng;
		 if (value < -180)
         {
             value = -180;
         }
         else if (value > 180)
         {
             value = 180;
         }
		this.lng = value;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return lng + ", " + lat;
	}
     
     
     

    
}
