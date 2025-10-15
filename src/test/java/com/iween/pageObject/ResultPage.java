package com.iween.pageObject;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.utilities.ScreenshotUtil;


public class ResultPage extends basePage{

	public ResultPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy(xpath="//img[@class='logo-width']")
	WebElement imageLogo;
	
	public void validateLogoInResultPage(ExtentTest test) {
		try {
			if (imageLogo.isDisplayed()) {
				test.log(Status.PASS, "Image is displayed on the Result Page");
			} else {
				test.log(Status.FAIL, "Image is not displayed on the Result Page");
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
	
	public boolean validateResultPage1(ExtentTest test) {
	    try {
	        // Try to find at least one flight card
	        List<WebElement> flightCards = driver.findElements(By.xpath("//*[contains(@class,'one-way-new-result-card')]"));

	        if (!flightCards.isEmpty() && flightCards.get(0).isDisplayed()) {
	            System.out.println("✅ Flight card is displayed successfully.");
	            test.log(Status.PASS, "✅ Flight card is displayed successfully.");
	            return true;
	        } else {
	            // Check if 'No Flights Found' message is shown
	            List<WebElement> noFlightMessages = driver.findElements(By.xpath("//div[text()='No Flights Found']"));

	            if (!noFlightMessages.isEmpty() && noFlightMessages.get(0).isDisplayed()) {
	                System.out.println("⚠️ No flight found for this search.");
	                test.log(Status.INFO, "⚠️ No flight found for this search.");
	                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, " No flight found for this search", " No flight found for this search");
	                Assert.fail();
	                return false;
	               
	            } else {
	                System.out.println("❌ Neither flight cards nor 'No Flights Found' message is present.");
	                test.log(Status.FAIL, "❌ Neither flight cards nor 'No Flights Found' message is present.");
	                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No flight cards or messages", "ResultPageValidationFailure");
	                Assert.fail();
	                return false;
	            }
	        }

	    } catch (Exception e) {
	        System.out.println("❌ Error while validating result page: " + e.getMessage());
	        test.log(Status.FAIL, "❌ Exception while validating result page: " + e.getMessage());
	        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception during result page check", "ResultPageException");
	        Assert.fail();
	        return false;
	    }
	}

	public void waitForProgressToComplete() {
	    
	    int timeoutInSeconds = 120;
	    int pollIntervalInMillis = 500; // Poll every 500ms
	    int elapsedTime = 0;

	    while (elapsedTime < timeoutInSeconds * 1000) {
	        try {
	            List<WebElement> progressElements = driver.findElements(By.cssSelector("div[role='progressbar']"));

	            // If progress element is no longer in the DOM, assume it is complete
	            if (progressElements.isEmpty()) {
	                break;
	            }

	            WebElement progress = progressElements.get(0);
	            String valueStr = progress.getAttribute("aria-valuenow");
	            
	            if (valueStr != null) {
	                try {
	                    int value = Integer.parseInt(valueStr);
	                    if (value >= 100) {
	                        // Wait a bit to ensure DOM update (if element disappears after 100%)
	                        Thread.sleep(300);
	                        if (driver.findElements(By.cssSelector("div[role='progressbar']")).isEmpty()) {
	                            break;
	                        }
	                    }
	                } catch (NumberFormatException e) {
	                    // Not a number, continue polling
	                }
	            }

	        } catch (StaleElementReferenceException e) {
	            // Element was removed after access - treat as done
	            break;
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            break;
	        }

	        try {
	            Thread.sleep(pollIntervalInMillis);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            break;
	        }

	        elapsedTime += pollIntervalInMillis;
	    }
	    System.out.println("Progress bar is completed");
	}
	public void validateResultPage(ExtentTest test) {
	    try {
	        List<WebElement> flightCards = driver.findElements(By.xpath("(//*[@class=' d-flex flex-column mb-2 one-way-new-result-card '])[1]"));
	 
	        if (!flightCards.isEmpty() && flightCards.get(0).isDisplayed()) {
	            System.out.println("✅ Flight card is displayed successfully.");
	            test.log(Status.PASS, "✅ Flight card is displayed successfully.");
	        } else {
	            List<WebElement> noFlightMessages = driver.findElements(By.xpath("//div[text()='No Flights Found']"));
	 
	            if (!noFlightMessages.isEmpty() && noFlightMessages.get(0).isDisplayed()) {
	                System.out.println("⚠️ No flight found for this search.");
	                test.log(Status.INFO, "⚠️ No flight found for this search.");
	            } else {
	                System.out.println("❌ Neither flight cards nor 'No Flights Found' message is present.");
	                test.log(Status.FAIL, "❌ Neither flight cards nor 'No Flights Found' message is present.");
	                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No flight cards or messages", "ResultPageValidationFailure");
	                Assert.fail();
	            }
	        }
	 
	    } catch (Exception e) {
	        System.out.println("❌ Error while validating result page: " + e.getMessage());
	        test.log(Status.FAIL, "❌ Exception while validating result page: " + e.getMessage());
	        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Exception during result page check", "ResultPageException");
	        Assert.fail();
	    }
	}
	
	public String selectFlightBasedOnIndexAndValidateAmenities(int index, ExtentTest test) {
	    try {
	        // Build XPath for the desired "View Price" button using the index
	        String viewPriceXPath = "(//*[contains(@class,'one-way-new-result-card')]//*[text()='View Price'])[" + index + "]";
	        WebElement viewPriceButton = driver.findElement(By.xpath(viewPriceXPath));

	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewPriceButton);
	        Thread.sleep(500);
	        viewPriceButton.click();
	        test.log(Status.PASS, "✅ Clicked 'View Price' for flight at index: " + index);
	        System.out.println("✅ Clicked 'View Price' for flight at index: " + index);

	        // Wait for the fare components section to be visible
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement fareMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='fare-components-list']")));

	        if (fareMenu.isDisplayed()) {
	            List<WebElement> fares = driver.findElements(By.xpath("//*[contains(@class,'fare-component-watermark')]"));

	            if (!fares.isEmpty()) {
	                WebElement firstFare = fares.get(0);

	                // Look for the Book now button within the selected fare block
	                WebElement bookNowBtn = firstFare.findElement(By.xpath(".//*[text()='Book now']"));
	                Thread.sleep(3000);
	                bookNowBtn.click(); 
	               
	                test.log(Status.PASS, "✅ 'Book now' button found for selected flight.");
	            } else {
	                test.log(Status.FAIL, "❌ No fare options available.");
	                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "No fares found", "FareOptionsMissing");
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("❌ Exception while selecting flight: " + e.getMessage());
	        test.log(Status.FAIL, "❌ Exception while selecting flight: " + e.getMessage());
	        ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Flight Selection Failure", "FlightSelectException");
	    }
		return null;
	}
	
	
	
	//-------------------------------START BELOW------------
	public void selectAirline()
	{
	  List<WebElement> airLine = driver.findElements(By.xpath("//div[text()='Search By Airlines']/parent::div//label"));
	  for(WebElement airLines :airLine)
	  {
		  String airLineFound = airLines.getText().split("\\(")[0].trim();
		  System.out.println(airLineFound);
		  if(airLineFound.contains("AIR INDIA"))
		  {
			  
			  airLines.click();
			  String clickedAirline = airLines.getText().split("\\(")[0].trim();;
			  System.out.println(clickedAirline);
			  List<WebElement> viewPrice = driver.findElements(By.xpath("//a[text()='View Price']"));
			  for(int i=0;i<viewPrice.size();i++)
			  {
				  viewPrice.get(i).click();
				 List<WebElement> supplierExist = driver.findElements(By.xpath("//span[text()='Supplier']/following-sibling::span"));
				 if(supplierExist.contains("TBO"))
				 {
					 System.out.println("all the faretype is from the supplier TBO");
				 }
				 List<WebElement> selectFareType = driver.findElements(By.xpath("//div[@class='d-flex justify-content-between flex-column h-100']/div/label"));
				 for(int j=0;j<selectFareType.size();j++)
				 {
					 selectFareType.get(j).click();
					 int jj = j+1;
					 WebElement clickedFareType = driver.findElement(By.xpath("(//span[text()='Fare Type']/parent::div/span[2])[jj]"));
					 String fareType = clickedFareType.getText();
					 System.out.println(fareType);	
					 
				 }
			  }
		  }
		  
		  
	  }
		
	}


}
