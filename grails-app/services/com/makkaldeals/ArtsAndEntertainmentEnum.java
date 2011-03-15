package com.makkaldeals;

public enum ArtsAndEntertainmentEnum implements SubcategoryEnum<ArtsAndEntertainmentEnum> {
	AP("Amusement Parks"),
	BO("Bowling"),
	LT("Laser Tag");

	private String description;

	private ArtsAndEntertainmentEnum(String description){
		this.description = description;
	}
	
	public String getValue(String subcategory) {
		return ArtsAndEntertainmentEnum.valueOf(subcategory).description;
	}
	
	public String getValue(ArtsAndEntertainmentEnum subcategory) {
		return subcategory.description;
	}

	@Override
	public ArtsAndEntertainmentEnum subCategory() {
		// TODO Auto-generated method stub
		return this;
	}

}
