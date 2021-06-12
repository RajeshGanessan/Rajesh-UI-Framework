package Utils.PageUtils;

import org.openqa.selenium.By;

public class DynamicXpath {
	
	public static By get(String xpath, String data) {
		
		String rawXapth = xpath.replaceAll("%replaceable%", data);
		return By.xpath(rawXapth);
	}

	public static By get(String xpath,int data){
		String rawXpath = xpath.replaceAll("%replaceable%", String.valueOf(data));
		return By.xpath(rawXpath);
	}
}
