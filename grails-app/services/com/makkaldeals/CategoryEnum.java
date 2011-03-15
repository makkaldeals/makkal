package com.makkaldeals;

public enum CategoryEnum { 
	
	
	
	AAE("Arts and Entertainment", ArtsAndEntertainmentEnum.AP), AU("Automative", AutomativeEnum.AGS)	;
	
	private String description;
	
	private SubcategoryEnum<?> subCategory; 
	
	private CategoryEnum(String description, SubcategoryEnum<?> subcategory) {
		this.description = description;
		
		this.subCategory = subcategory;
	}
	
	public String getDescription(String category) {
		return CategoryEnum.valueOf(category).description;
	}
	
	public String getDescription(CategoryEnum category) {
		return category.description;
	}

	public SubcategoryEnum<?> getSubcategories(String category) {
		// TODO Auto-generated method stub
		return subCategory;
	}	
}
