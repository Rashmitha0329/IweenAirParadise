package com.iween.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.utilities.ScreenshotUtil;

public class BookingPage extends basePage{

	public BookingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath="//img[@class='logo-width']")
	WebElement imageLogo;
	
	public void validateLogoInBookingPage(ExtentTest test) {
		try {
			if (imageLogo.isDisplayed()) {
				test.log(Status.PASS, "Image is displayed on the Booking Page");
			} else {
				test.log(Status.FAIL, "Image is not displayed on the Booking Page");
				ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, 
						"Image is not displayed", "LogoImageIsBroken");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Exception occurred while validating logo: " + e.getMessage());
			ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, 
					"Exception while validating logo", "LogoValidationException");
			 Assert.fail();
		}
	}


}
