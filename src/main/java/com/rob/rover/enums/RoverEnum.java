package com.rob.rover.enums;

public enum RoverEnum {
	CURIOSITY("curiosity"),
	OPPORTUNITY("opportunity"),
	SPIRIT("spirit");

	private String value;

	RoverEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
