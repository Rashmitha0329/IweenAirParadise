package com.iween.AirParadiseClientPanelTestCase;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.pageObject.LoginPage;
import com.iween.pageObject.ResultPage;
import com.iween.pageObject.BookingPage;
import com.iween.pageObject.HomePage;
import com.iween.testBase.baseClass;
import com.iween.utilities.DataProviders;
import com.iween.utilities.ExtentManager;
import com.iween.utilities.Iween_FutureDates;
import com.iween.utilities.Retry;
import com.iween.utilities.ScreenshotUtil;




public class TCCP_1_LogoAppearance extends baseClass {

    @Test(dataProvider = "excelData", dataProviderClass = DataProviders.class, retryAnalyzer = Retry.class)
    public void myTest(Map<String, String> excelTestData) throws Exception {

        ExtentTest test = ExtentManager.getTest();  // Get the ExtentTest instance from thread-local
        logger.info("******** Starting TestCase1: testLogin ********");
        
        

        try {
        	   // Log the data being used
            System.out.println("Running test with: " + excelTestData);
        	test.log(Status.INFO, "Search To Booking Supplier Wise");
        	//Get The Data From Excel
            String departFrom = excelTestData.get("Depart From");
            String goingTo = excelTestData.get("Going To");
    		String adultsCounts = excelTestData.get("AdultsCounts");
    		String childCount = excelTestData.get("ChildrenCount");
    		String infantsCount = excelTestData.get("InfantsCount");
    		 String Class = excelTestData.get("TravelClass");
    
    		 

    		 	
    		 //Method To Get Future Date
    		  Map<String, Iween_FutureDates.DateResult> dateResults = futureDates.furtherDate();
      		Iween_FutureDates.DateResult date30 = dateResults.get("datePlus30");
      		String fromMonthYear = date30.month + " " + date30.year;
    		
    		 test.log(Status.INFO, "Depart From: " + departFrom +",Going To: "+ goingTo+",Selected Class: "+Class);
   	        test.log(Status.INFO, "AdultsCounts: " + adultsCounts + ", ChildrenCount: " + childCount + ", InfantsCount: " + infantsCount);
   	 
   	    test.log(Status.INFO, "Flight OnWardDate:" +" "+date30.day+" "+fromMonthYear);
     
    		
            // Login page object
            LoginPage loginPage = new LoginPage(driver);
            HomePage homePage = new HomePage(driver);
            ResultPage resultPage = new ResultPage(driver);
            BookingPage bookingPage = new BookingPage(driver);
            
            loginPage.validateLoginPageIsDisplayed(test);
            Thread.sleep(500);
             loginPage.validateLogoInLoginPage(test);
             Thread.sleep(500);
        

            // Perform login using values from properties file
            loginPage.UserLogin(p.getProperty("username"), p.getProperty("password"));
            
            long startTime = System.currentTimeMillis();
            loginPage.clickOnSubmitButton();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Search Flights']")));
            long endTime = System.currentTimeMillis();
    		long loadTimeInSeconds = (endTime - startTime) / 1000;
    		test.log(Status.INFO, "Flight login page  loaded in " + loadTimeInSeconds + " seconds");
    		
    		Thread.sleep(3000);
    		resultPage.validateLogoInResultPage(test);
    		Thread.sleep(3000);
    		homePage.validateHomePageIsDisplayed(test);
    		
    		 long startTime1 = System.currentTimeMillis();
            
            
     		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Search Flights']")));
             long endTime1 = System.currentTimeMillis();
     		long loadTimeInSeconds1 = (endTime1 - startTime1) / 1000;
     		test.log(Status.INFO, "Flight Home page  loaded in " + loadTimeInSeconds1 + " seconds");
            
     	//	SearchPage.validateHomePageIsDisplayed(test);
            
            
               
     		homePage.validateHomePageIsDisplayed(test);
     		
     		Thread.sleep(500);
     		
     		homePage.validateLogoInHomePage(test);
   

     		homePage.searchFightsOnHomePage(departFrom,goingTo,date30.day,fromMonthYear,adultsCounts,childCount,infantsCount,Class);

     		long startTime2 = System.currentTimeMillis();

            // Click the Search button
     		homePage.clickOnSearch(test);

            // Define wait
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));

            boolean isResultLoaded = false;

            try {
                // Wait for flight result cards (primary indicator)
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'one-way-new-result-card')]")));
                isResultLoaded = true;
            } catch (TimeoutException e) {
                // If primary element not found, optionally use backup check or validation method
                test.log(Status.WARNING, "Flight cards not found within wait time. Trying page validation fallback...");
                isResultLoaded = resultPage.validateResultPage1(test); // This method must return boolean
            }

            // End timing
            long endTime2 = System.currentTimeMillis();
            long loadTimeInSeconds2 = (endTime2 - startTime2) / 1000;

            if (isResultLoaded) {
                test.log(Status.PASS, "✅ Flight search results loaded in " + loadTimeInSeconds2 + " seconds.");
            } else {
                test.log(Status.FAIL, "❌ Flight search results did not load in time.");
                ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Result Load Timeout", "No results within expected time.");
            }
            resultPage. waitForProgressToComplete();
            resultPage.validateResultPage(test);
            Thread.sleep(500);
            resultPage.validateLogoInResultPage(test);
            Thread.sleep(500);
            resultPage.selectFlightBasedOnIndexAndValidateAmenities(1,test);  
            Thread.sleep(500);
            bookingPage.validateLogoInBookingPage(test);
            
            
    		
   		Thread.sleep(5000);
   		logger.info("******** TestCase1: testLogin completed successfully ********");

        } catch (Exception e) {
            logger.error("Test failed due to: ", e);
            test.fail("Test failed with exception: " + e.getMessage());
            throw e;  // Re-throw to ensure Retry works properly
        }
    
	
}
}
