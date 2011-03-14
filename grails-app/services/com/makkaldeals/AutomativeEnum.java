package com.makkaldeals;

public enum AutomativeEnum implements SubcategoryEnum<AutomativeEnum> {
	AGS("Auto Glass Service"),
	APA("Auto Parts  &   Accessories"),
	ARS(" Auto Repair &    Services");
	
	private String description;

	private AutomativeEnum(String description){
		this.description = description;
	}
	
	public String getValue(String subcategory) {
		return AutomativeEnum.valueOf(subcategory).description;
	}
	
	public String getValue(AutomativeEnum subcategory) {
		return subcategory.description;
	}

	@Override
	public AutomativeEnum[] subCategoryValues() {
		// TODO Auto-generated method stub
		return AutomativeEnum.values();
	}	
}
