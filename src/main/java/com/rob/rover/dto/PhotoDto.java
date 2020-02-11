package com.rob.rover.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PhotoDto {
	private Integer id;
	private Integer sol;
	@JsonProperty("img_src")
	private String imgSrc;
	@JsonProperty("earth_date")
	private String earthDate;
	// camera object not included
	// rover object not included


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSol() {
		return sol;
	}

	public void setSol(Integer sol) {
		this.sol = sol;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getEarthDate() {
		return earthDate;
	}

	public void setEarthDate(String earthDate) {
		this.earthDate = earthDate;
	}
}
