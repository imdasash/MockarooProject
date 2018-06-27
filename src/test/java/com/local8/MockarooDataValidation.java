package com.local8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {

	WebDriver driver;
	String url = "https://mockaroo.com/";
	FileReader firstLineActual;
	BufferedReader fileAction;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(url);
	}

	@Test
	(priority = 1)
	public void MockarooTitleVerification() {

		Assert.assertTrue(driver.getTitle().contains("Mockaroo - Random Data Generator"));
	}

	@Test
	(priority = 2)
	public void MockarooNameVerification() {
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='tagline']")).getText().contains("realistic data generator"));

	}
	
	@Test
	(priority = 3)
	public void removeFields() throws InterruptedException {
		for(int i = 1; i < 7; i++) {
		driver.findElement(By.xpath("(//a[@class='close remove-field remove_nested_fields'])["+i+"]")).click();;
		
		}
		
    }
	
	@Test
	(priority = 4)
	public void lableVerification() {
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-name']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-type']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-options']")).isDisplayed());
	}
	
	@Test
	(priority = 5)
	public void buttonVerification() { 
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).isEnabled());
	}
	
	@Test
	(priority = 6)
	public void rowsVerification() {
		Assert.assertEquals(driver.findElement(By.id("num_rows")).getAttribute("value"), "1000");
	}
	
	@Test
	(priority = 7)
	public void formatVerification() {
		
		Select dropdown = new Select(driver.findElement(By.id("schema_file_format")));
		
		Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "CSV");
	}
	@Test
	(priority = 8)
	public void LineEndingVerification() {
		
		Select dropdown = new Select(driver.findElement(By.id("schema_line_ending")));
		
		Assert.assertEquals(dropdown.getFirstSelectedOption().getText(), "Unix (LF)");
	}
	
	@Test
	(priority = 9)
	public void headerAndBOMCheckBoxes() {
		Assert.assertTrue(driver.findElement(By.id("schema_include_header")).isSelected() &&
				(!driver.findElement(By.id("schema_bom")).isSelected()));
	}
	
	@Test
	(priority = 10)
	public void addAnotherCity() {
		
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("(//input[@class='column-name form-control'][@type='text'])[7]")).sendKeys("City");
		
	}
	
	@Test
	(priority = 11)
	public void ChooseTypeVerification() throws InterruptedException {
		//driver.findElement(By.xpath("(//input[@class='btn btn-default'][@type='text'])[7]")).click();
		driver.findElement(By.xpath("(//input[@class='btn btn-default'][@type='text'])[7]")).click();
		Thread.sleep(2000);
		Assert.assertTrue(driver.findElement(By.xpath("//h3[@class='modal-title'][1]")).getText().contains("Choose a Type"));
	}
	
	@Test
	(priority = 12)
	public void searchForCity() throws InterruptedException  {
		driver.findElement(By.id("type_search_field")).sendKeys("City");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@tabindex='1']")).click();
		Thread.sleep(2000);
		
	}
	@Test
	(priority = 13)
	public void addAnotherCity2() {
		
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("(//input[@class='column-name form-control'][@type='text'])[8]")).sendKeys("Country");
		
	}
	
	@Test
	(priority = 14)
	public void ChooseTypeVerification2() throws InterruptedException {
		
		driver.findElement(By.xpath("(//input[@class='btn btn-default'][@type='text'])[8]")).click();
		Thread.sleep(2000);
		Assert.assertTrue(driver.findElement(By.xpath("//h3[@class='modal-title'][1]")).getText().contains("Choose a Type"));
	}
	
	@Test
	(priority = 15)
	public void searchForCity2() throws InterruptedException  {
		driver.findElement(By.id("type_search_field")).sendKeys("Country");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@tabindex='1']")).click();
		Thread.sleep(2000);
		
	}
	@Test
	(priority = 16)
	public void clickDownload() throws InterruptedException  {
		driver.findElement(By.id("download")).click();
		
		Thread.sleep(5000);
		
		
	}
	@Test(priority = 17)
	public void runner() throws IOException {

		// 17. Open the downloaded file using BufferedReader.

		List<String> countries = new ArrayList<String>();
		FileReader fr = new FileReader("/Users/oleksandrdanylchuk/Downloads/MOCK_DATA.csv");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		int count = 0;
		while ((line = br.readLine()) != null) {
			countries.add(line);
			count++;
		}

		// 18. Assert that first row is matching with Field names that we selected.

		String[] arr = countries.get(0).split(",");
		System.out.println(arr[0] + " " + arr[1]);

		Assert.assertTrue((arr[0] + " " + arr[1]).equals("City Country"));

		// 19. Assert that there are 1000 records

		Assert.assertTrue(count == 1001);

		// 20. Add all countries to Countries ArrayList

		List<String> countriesList = new ArrayList<String>();
		for (int i = 1; i < countries.size(); i++) {
			String[] arr1 = countries.get(i).split(",");
			countriesList.add(arr1[1]);
		}

		// 21. From file add all Cities to Cities ArrayList

		List<String> citiesList = new ArrayList<String>();
		for (int i = 1; i < countries.size(); i++) {
			String[] arr1 = countries.get(i).split(",");
			citiesList.add(arr1[0]);
		}

		// 22. Sort all cities and find the city with the longest name and shortest name

		Collections.sort(citiesList);
		Collections.sort(countriesList);
		int MAX = 0;
		String longCity = "";
		for (int i = 0; i < citiesList.size(); i++) {
			if (citiesList.get(i).length() > MAX) {
				MAX = citiesList.get(i).length();
				longCity = citiesList.get(i);
			}
		}
		System.out.println("Long city name length: " + longCity + "  " + MAX);

		int MIN = MAX;
		String shortCity = "";
		for (int i = 0; i < citiesList.size(); i++) {
			if (citiesList.get(i).length() < MIN) {
				MIN = citiesList.get(i).length();
				shortCity = citiesList.get(i);
			}
		}
		System.out.println("Short city name length: " + shortCity + " " + MIN);

		// 23. In Countries ArrayList, find how many times each Country is mentioned.
		// and print out

		System.out.println("Countries frequency by cities: ");
		HashSet<String> Countries = new HashSet<String>(countriesList);
		for (String country : Countries) {
			System.out.println(country + "-" + Collections.frequency(countriesList, country));
		}

		// 24. From file add all Cities to citiesSet HashSet

		HashSet<String> citySet = new HashSet<String>(citiesList);

		// 25. Count how many unique cities are in Cities ArrayList and ...

		List<String> uniqueCities = new ArrayList<String>();
		for (String city : citiesList) {
			if (!uniqueCities.contains(city))
				uniqueCities.add(city);
		}

		// .. assert that it is matching with the count of citiesSet HashSet.

		Assert.assertEquals(uniqueCities.size(), citySet.size());
		System.out.println("uniqueCities numbers: " + uniqueCities.size());
		System.out.println("citySet numbers: " + citySet.size());

		// 26. Add all Countries to countrySet HashSet

		HashSet<String> countrySet = new HashSet<String>(countriesList);

		// 27. Count how many unique countries are in Countries ArrayList ...

		List<String> uniqueCountries = new ArrayList<String>();
		for (String country : countriesList) {
			if (!uniqueCountries.contains(country))
				uniqueCountries.add(country);
		}

		// ...and assert that it is matching with the count of countrySet HashSet.

		Assert.assertEquals(uniqueCountries.size(), countrySet.size());
		System.out.println("uniqueCountries number : " + uniqueCountries.size());
		System.out.println("Countries number: " + countrySet.size());
		
		
		
		
		driver.close();

	}
	
}
