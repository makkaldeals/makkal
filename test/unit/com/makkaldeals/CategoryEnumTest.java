package com.makkaldeals;

import static org.junit.Assert.*;

import org.junit.Test;

public class CategoryEnumTest {

	@Test
	public void testAutomative() {
		assertEquals("Code for Automative Category", "AU", CategoryEnum.AU.toString());
		String categoryCode = CategoryEnum.AU.toString();
		assertEquals("Description for AU Category", "Automative", CategoryEnum.valueOf(categoryCode).getDescription(categoryCode));
	}
	
	@Test
	public void testArtsAndEntertainment() {
		assertEquals("Code for Automative Category", "AAE", CategoryEnum.AAE.toString());
		String categoryCode = CategoryEnum.AAE.toString();
		assertEquals("Description for AAE Category", "Arts and Entertainment", CategoryEnum.valueOf(categoryCode).getDescription(categoryCode));
	}	
	
	@Test
	public void testCategoryValues() {
		assertEquals("The number of category are ", 2, CategoryEnum.values().length);
	}
	
	@Test
	public void testAutomativeSubCategories() {
		SubcategoryEnum<AutomativeEnum>[] automativeSubCategories = (SubcategoryEnum<AutomativeEnum>[]) CategoryEnum.AU.getSubcategories("AU");
		
		
		for(SubcategoryEnum<AutomativeEnum> enume : automativeSubCategories) {
			System.out.println("How many times");
			for (AutomativeEnum autoenum : enume.subCategoryValues()) {
				System.out.println(autoenum.getValue(autoenum.AGS));
			}
		}
	}
}	

