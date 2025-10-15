package com.iween.pageObject;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.iween.utilities.ScreenshotUtil;

public class SearchPage extends basePage{

	public SearchPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@FindBy(id="prefclass")
	WebElement classDropdown; 
	
	@FindBy(xpath="(//div[contains(@class,'react-select__input-container')]/input)[1]")
	WebElement fromLocation;

	@FindBy(xpath="(//div[contains(@class,'react-select__input-container')]/input)[2]")
	WebElement toLocation;

	@FindBy(xpath = "(//div[@class='react-datepicker__input-container'])[1]")
	WebElement datePickerInput;

	@FindBy(xpath = "(//div[@class='react-datepicker__current-month'])[1]")
	WebElement date;

	@FindBy(xpath = "//button[@aria-label='Next Month']")
	WebElement nextMonth;


	@FindBy(xpath = "(//div[@class='react-datepicker__header ']/child::div)[1]")
	WebElement MonthYear;

	@FindBy(xpath="//span[@class='travellers-class_text']")
	WebElement clickOnClassPassangerDropdown;

	@FindBy(xpath="//button[text()='Done']")
	WebElement doneButton;

	@FindBy(xpath="//button[text()='Search Flights']")
	WebElement searchFlight;
	public void searchFightsOnHomePage(String from, String to, String day, String MonthandYear, String adult, String child, String infant,String travelClass) {
		try {
 
 
			Thread.sleep(1000);
			enterFromLocation(from);
			Thread.sleep(1000);
			enterToLocation(to);
			Thread.sleep(1000);
			selectDate(day, MonthandYear);
			Thread.sleep(1000);
 
			clickOnClassPassangerDropdown.click();
			Thread.sleep(1000);
			addAdult(adult);
			Thread.sleep(1000);
			addChild(child);
			Thread.sleep(1000);
			infantCount(infant);
			Thread.sleep(1000);
			selectTravelClass(travelClass);
			Thread.sleep(1000);
			doneButton.click();
 
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Best practice to reset the interruption status
			System.out.println("Interrupted while searching flights on home page: " + e.getMessage());
			Assert.fail();
		} catch (Exception e) {
			System.out.println("Error in searchFightsOnHomePage(): " + e.getMessage());
			Assert.fail();
		}
	}
 
//Method to enter From Location
	public void enterFromLocation(String from) {
		try {
			fromLocation.clear();
			fromLocation.sendKeys(from);
			location(from);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
 
public void enterToLocation(String to) {
		try {
			toLocation.clear();
			toLocation.sendKeys(to);
			location(to);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
 
public void selectDate(String day, String MonthandYear) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Method A: Using zoom
		js.executeScript("document.body.style.zoom='80%'");
 
		datePickerInput.click();
		String Date = date.getText();
		//	String Date=driver.findElement(By.xpath("(//h2[@class='react-datepicker__current-month'])[1]")).getText();
		System.out.println(day +" "+MonthandYear);
		System.out.println(Date);
		if(Date.contentEquals(MonthandYear))
		{
			Thread.sleep(4000);
			driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
			Thread.sleep(4000);
		}else {
			while(!Date.contentEquals(MonthandYear))
			{
				Thread.sleep(500);
				nextMonth.click();
				Thread.sleep(500);
				date.getText();
				if(MonthYear.getText().contentEquals(MonthandYear))
				{
					 List<WebElement> dates = driver.findElements(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']"));
					 dates.getLast().click();
					break;
				}
 
			}
		}
	}
 
 

public void addAdult(String adult) {
		try {
			driver.findElement(By.xpath("//span[text()='Adults(12y+)']/parent::div//li[text()='" + adult + "']")).click();
		} catch (Exception e) {
			System.out.println("Error in addAdult(): " + e.getMessage());
			Assert.fail();
		}
	}
 
public void addChild(String child) {
		try {
			driver.findElement(By.xpath("//span[text()='Children(2y-12y)']/parent::div//li[text()='" + child + "']")).click();
		} catch (Exception e) {
			System.out.println("Error in addChild(): " + e.getMessage());
			Assert.fail();
		}
	}
 
	public void infantCount(String infant) {
		try {
			driver.findElement(By.xpath("//span[text()='Infants(<2y)']/parent::div//li[text()='"+infant+"']")).click();
		} catch (Exception e) {
			System.out.println("Error in infantCount(): " + e.getMessage());
			Assert.fail();
		}
	}
 
public void selectTravelClass(String travelClass) {
		try {
			classDropdown.click();
			//   JavascriptExecutor js = (JavascriptExecutor) driver;
			//   js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
 
			Select select = new Select(classDropdown);
			select.selectByVisibleText(travelClass);
		} catch (Exception e) {
			System.out.println("Error in selectTravelClass(): " + e.getMessage());
		}
	}
 
//Method to select City.
	public void location(String location) throws TimeoutException {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Wait for dropdown container to appear
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@role='listbox']")));

			// Wait until options are loaded
			wait.until(driver -> driver.findElements(By.xpath("//span[@class='airport-option_country-code']")).size() > 0);

			List<WebElement> initialOptions = driver.findElements(By.xpath("//span[@class='airport-option_country-code']"));
			int bestScore = Integer.MAX_VALUE;
			String bestMatchText = null;

			String input = location.trim().toLowerCase();

			for (int i = 0; i < initialOptions.size(); i++) {
				try {
					WebElement option = driver.findElements(By.xpath("//span[@class='airport-option_country-code']")).get(i);
					String suggestion = option.getText().trim().toLowerCase();
					int score = levenshteinDistance(input, suggestion);

					if (score < bestScore) {
						bestScore = score;
						bestMatchText = option.getText().trim();
					}
				} catch (StaleElementReferenceException e) {
					System.out.println("Stale element at index " + i + ", skipping.");
				}
			}

			if (bestMatchText != null) {
				// Retry clicking best match up to 3 times
				int attempts = 0;
				boolean clicked = false;
				while (attempts < 3 && !clicked) {
					try {
						WebElement bestMatch = wait.until(ExpectedConditions.elementToBeClickable(
								By.xpath("//span[@class='airport-option_country-code'][text()='" + bestMatchText + "']")));
						bestMatch.click();
						System.out.println("Selected best match: " + bestMatchText);
						clicked = true;
					} catch (StaleElementReferenceException e) {
						System.out.println("Stale element on click attempt " + (attempts + 1) + ", retrying...");
					}
					attempts++;
				}

				if (!clicked) {
					System.out.println("Failed to click the best match after retries.");
				}

			} else {
				System.out.println("No suitable match found for input: " + location);
			}

		} catch (NoSuchElementException e) {
			System.out.println("Input or dropdown not found: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected error while selecting city or hotel: " + e.getMessage());
		}
	}

	public int levenshteinDistance(String a, String b) {
		int[][] dp = new int[a.length() + 1][b.length() + 1];

		for (int i = 0; i <= a.length(); i++) {
			for (int j = 0; j <= b.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
					dp[i][j] = Math.min(
							Math.min(dp[i - 1][j] + 1,     // deletion
									dp[i][j - 1] + 1),    // insertion
							dp[i - 1][j - 1] + cost); // substitution
				}
			}
		}
		return dp[a.length()][b.length()];
	}
	public void clickOnSearch(ExtentTest test) {
		try {
			WebElement searchBtn = driver.findElement(By.xpath("//button[text()='Search Flights']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchBtn);
			Thread.sleep(300);
			searchBtn.click();
			System.out.println("✅ Clicked on 'Search Flights' button.");
			test.log(Status.PASS, "✅ Clicked on 'Search Flights' button.");
			areYouSurePopUp();
		} catch (Exception e) {
			System.out.println("❌ Failed to click 'Search Flights' button: " + e.getMessage());
			test.log(Status.FAIL, "❌ Failed to click 'Search Flights' button: " + e.getMessage());
			ScreenshotUtil.captureAndAttachScreenshot1(driver, test, Status.FAIL, "Search Click Failure", "SearchButtonClickException");
			Assert.fail();
		}
	}
	public void areYouSurePopUp() {
		try {
			List<WebElement> popups = driver.findElements(By.xpath("//div[@class='fade app-modal help-support-modal modal show']//div[text()='Are You Sure?']/parent::div/parent::div//button[text()='Yes, Continue']"));

			if (!popups.isEmpty() && popups.get(0).isDisplayed()) {
				popups.get(0).click();
				System.out.println("✅ 'Are You Sure?' popup found and 'Yes, Continue' clicked.");
			} else {
				System.out.println("ℹ️ No 'Are You Sure?' popup found.");
			}
		} catch (Exception e) {
			System.out.println("❌ Exception while handling popup: " + e.getMessage());
		}
	}

}
