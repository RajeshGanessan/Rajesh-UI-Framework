package Utils.PageUtils;

import java.io.File;
import java.io.FileFilter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class HelperMethods  {

	 WebDriver driver;
	 ElementUtils elementUtils;
	 private Random rand = new Random();

	public HelperMethods(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
	}

	static String currentWindow = "";



	public void scrollDownComplete() throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		js.executeScript("scroll(0, 700);");
		Thread.sleep(3000);
	}

	//Scrolling to the Top of the page
	public void scrollUp() {
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("window.scrollTo(0, 0);");
	}

	// Scrolls till the bottom of page
	public  void scrolltoBottomOfPage() {
		try {
			long height = Long.parseLong(
					((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());

			while (true) {
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(500);

				long newHeight = Long.parseLong(
						((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());
				if (newHeight == height) {
					break;
				}
				height = newHeight;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void scrollIntoView(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}


	// get random Number
	public int getRandomNumber() {
		return rand.nextInt(1000);
	}

	// get random Number with Value
	public  String getRandomNumberValue(int value) {
		int randomNum = rand.nextInt(value);
		if(randomNum==2){
			getRandomNumberValue(value);
		}
		return Integer.toString(randomNum);
	}

	public String getRandomVestedOptionValue(int value){
		int randomNum = rand.nextInt(value);
		if(randomNum == 0){
			randomNum++;
		}
		return Integer.toString(randomNum);
	}

	// Getting modded file
	public  static String lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice.getAbsoluteFile().toString();
	}

	// Get randomFile from directory
	public  String getRandomFile() {

		String testDocsPath = System.getProperty("user.dir") + "/resources/TestDocs";
		File directory = new File(testDocsPath);

		if (directory.exists()) {

			String[] files = directory.list();
			if (files == null) {
				throw new AssertionError();
			}
			int randomFile = rand.nextInt(files.length);
			return testDocsPath + "/" + files[randomFile];
		} else {
			System.out.println("TestDocs Directory not found");
			return null;
		}
	}

	//MoveToElement
	public void moveToElement(By locator){
		Actions actions = new Actions(driver);
		actions.moveToElement(elementUtils.getElement(locator)).perform();
	}

	// Selecting year from dropdown
	private void selectYear(By arrowLocator) {
		moveToElement(arrowLocator);
		for (int i = 0; i < 3; i++) elementUtils.doClick(arrowLocator);
	}

	private String getDate() {
		LocalDate localDate= LocalDate.now();
		DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MMMM d, yyyy");
		return formatter.format(localDate.minusYears(3));
	}

	// Selecting date from calender
	public void selectDateFromCalender(By arrowLocator,By dateField,By calTitle ) {
		String selectDate = "//span[@aria-label='%replaceable%']";
		String dateToSelect = getDate();
		elementUtils.doClick(dateField);
		if (elementUtils.doIsDisplayed(calTitle)) {
			selectYear(arrowLocator);
			driver.findElement(DynamicXpath.get(selectDate, dateToSelect)).click();
		}
	}

	// for dynamic dropdown;
	public  void selectFromDropdown(String searchTerm, By locator) {
		try {
			String valueXpath = "//div[contains(@class,'option') and contains(text(),'%replaceable%')]";
			elementUtils.doSendKeys(locator,searchTerm);
			elementUtils.waitForElementVisibility(DynamicXpath.get(valueXpath,searchTerm));
			driver.findElement((DynamicXpath.get(valueXpath, searchTerm))).click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed to select the option, check the element");
		}
	}

	//for dynamic Drop - Select using Option Index;
	public void selectRandomValueFromList(String optionToSelect, By locator) {
		try {
			String idXpath = "//div[contains(@class,'option')]";
			elementUtils.doSendKeys(locator,optionToSelect);
			elementUtils.waitForElementVisibility(By.xpath(idXpath));
			int size = driver.findElements(By.xpath(idXpath)).size();
			String randomValue = getRandomNumberValue(size);
			elementUtils.waitForElementVisibility(By.xpath("//div[contains(@class,'option') and contains(@id,'"+randomValue+"')]"));
			driver.findElement(By.xpath("//div[contains(@class,'option') and contains(@id,'"+randomValue+"')]")).click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed To select the option, Check the element");
		}
	}

}
