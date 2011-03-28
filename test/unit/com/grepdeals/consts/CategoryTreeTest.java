package com.grepdeals.consts;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.grepdeals.consts.CategoryTree;
import org.junit.Test;

public class CategoryTreeTest {

	@Test
	public void testAllCategories() {
		List<String> expectedCategories = new ArrayList<String>();
		expectedCategories.add(CategoryTree.Automotive.toString());
		expectedCategories.add(CategoryTree.FinancialServices.toString());

		assertEquals("Categories and FinancialServices", expectedCategories.toString(), CategoryTree.Category.children().toString());
	}

	
	@Test
	public void testAutomotiveCode() {
		assertEquals("Code for Automative Category", "Automotive", CategoryTree.Automotive.toString());

	}
	
	@Test
	public void testAutomotiveSubcategories() {
		List<String> expectedSubCategories = new ArrayList<String>();
		expectedSubCategories.add(CategoryTree.AutoGlassServices.toString());
		expectedSubCategories.add(CategoryTree.AutoRepairServices.toString());
		expectedSubCategories.add(CategoryTree.AutoPartsAccessories.toString());		
		expectedSubCategories.add(CategoryTree.BodyShopsPainting.toString());		
		expectedSubCategories.add(CategoryTree.CarDealers.toString());	
		expectedSubCategories.add(CategoryTree.CarWashDetailing.toString());		
		expectedSubCategories.add(CategoryTree.GasServicesStations.toString());		
		expectedSubCategories.add(CategoryTree.MotorcycleDealers.toString());	
		expectedSubCategories.add(CategoryTree.MotorcycleRepair.toString());		
		expectedSubCategories.add(CategoryTree.OilChange.toString());	
		expectedSubCategories.add(CategoryTree.Parking.toString());		
		expectedSubCategories.add(CategoryTree.ScootersAndMopeds.toString());
		expectedSubCategories.add(CategoryTree.StereoInstallation.toString());			
		expectedSubCategories.add(CategoryTree.TiresAndWheels.toString());		
		expectedSubCategories.add(CategoryTree.Towing.toString());		
	
		List<? extends CategoryTree> actualSubCategories = CategoryTree.Automotive.allChildren();
		
		assertEquals("Children for Automobile ",expectedSubCategories.toString(), actualSubCategories.toString());
	}	
	
}
