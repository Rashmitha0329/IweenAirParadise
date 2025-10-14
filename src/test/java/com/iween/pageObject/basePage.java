
package com.iween.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class basePage {

	 // Constructor of BasePage to initialize driver and PageFactory elements
	WebDriver driver;
	
	public basePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);// initialize all @FindBy elements in this class and subclasses
	}
}
