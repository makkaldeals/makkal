package com.makkaldeals.consts;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.makkaldeals.consts.CategoryTree;
import org.junit.Test;

public class CategoryTreeTest {

	@Test
	public void testAllCategories() {
		List<String> expectedCategories = new ArrayList<String>();
		expectedCategories.add(CategoryTree.AU.toString());

		assertEquals("Categories", expectedCategories.toString(), CategoryTree.Category.children().toString());
	}

	
	@Test
	public void testAutomotiveCode() {
		assertEquals("Code for Automative Category", "AU", CategoryTree.AU.toString());
		System.out.println(CategoryTree.Category.children().toString());
	}
	
	@Test
	public void testAutomotiveSubcategories() {
		List<String> expectedSubCategories = new ArrayList<String>();
		expectedSubCategories.add(CategoryTree.AGS.toString());
		expectedSubCategories.add(CategoryTree.APA.toString());
		expectedSubCategories.add(CategoryTree.ARS.toString());		
		expectedSubCategories.add(CategoryTree.BSP.toString());		
		expectedSubCategories.add(CategoryTree.CD.toString());	
		expectedSubCategories.add(CategoryTree.CWD.toString());		
		expectedSubCategories.add(CategoryTree.GSS.toString());		
		expectedSubCategories.add(CategoryTree.MD.toString());	
		expectedSubCategories.add(CategoryTree.MR.toString());		
		expectedSubCategories.add(CategoryTree.OC.toString());	
		expectedSubCategories.add(CategoryTree.P.toString());		
		expectedSubCategories.add(CategoryTree.SM.toString());
		expectedSubCategories.add(CategoryTree.SI.toString());			
		expectedSubCategories.add(CategoryTree.TW.toString());		
		expectedSubCategories.add(CategoryTree.TO.toString());		
		
		
		List<? extends CategoryTree> actualSubCategories = CategoryTree.AU.allChildren();
		
		assertEquals("Children for Automobile ",expectedSubCategories.toString(), actualSubCategories.toString());
	}	
	
}
