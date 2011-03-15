package com.makkaldeals;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
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
		System.out.println("---------- Categories ------");
		
		for(CategoryEnum catEnum : CategoryEnum.values()) {
			System.out.println(catEnum.toString()+"-"+catEnum.getDescription(catEnum));
		}
		System.out.println("---------- ---------- ------\n");
	}
	
	@Test
	public void testAutomativeSubCategories() {
		
		System.out.println("---------- Automative SubCategories ------");
		SubcategoryEnum<AutomativeEnum> automative = (SubcategoryEnum<AutomativeEnum>) CategoryEnum.AU.getSubcategories("AU");
		for (AutomativeEnum autoSubenum : automative.subCategory().values()) {
				System.out.println(autoSubenum.toString()+"-"+autoSubenum.getValue(autoSubenum));
		}
		System.out.println("---------- ---------- ------\n");
	}
	
	@Test
	public void testArtsAndEntertainmentSubCategories() {
		System.out.println("---------- Arts and Entertainment SubCategories ------");
		SubcategoryEnum<ArtsAndEntertainmentEnum> artsAndEntertainment = (SubcategoryEnum<ArtsAndEntertainmentEnum>) CategoryEnum.AU.getSubcategories("AU");
		for (ArtsAndEntertainmentEnum artsAndEntertainmentSub : artsAndEntertainment.subCategory().values()) {
				System.out.println(artsAndEntertainmentSub.toString()+"-"+artsAndEntertainmentSub.getValue(artsAndEntertainmentSub));
		}
		System.out.println("---------- ---------- ------\n");
	}	

}	

