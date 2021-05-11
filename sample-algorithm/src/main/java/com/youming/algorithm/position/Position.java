package com.youming.algorithm.position;

import java.math.BigDecimal;

public class Position {

	private Integer hemisphereLongitude;		//经度半球
	private BigDecimal longitude;		//经度
	private Integer hemisphereLatitude;		//纬度半球
	private BigDecimal latitude;		//纬度
	private BigDecimal altitude;		//海拔
	private Integer azimuth;		//方位;
	
	
	public Integer getHemisphereLongitude() {
		return hemisphereLongitude;
	}
	public void setHemisphereLongitude(Integer hemisphereLongitude) {
		this.hemisphereLongitude = hemisphereLongitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public Integer getHemisphereLatitude() {
		return hemisphereLatitude;
	}
	public void setHemisphereLatitude(Integer hemisphereLatitude) {
		this.hemisphereLatitude = hemisphereLatitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getAltitude() {
		return altitude;
	}
	public void setAltitude(BigDecimal altitude) {
		this.altitude = altitude;
	}
	public Integer getAzimuth() {
		return azimuth;
	}
	public void setAzimuth(Integer azimuth) {
		this.azimuth = azimuth;
	}
	

}
