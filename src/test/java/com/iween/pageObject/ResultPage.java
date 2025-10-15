package com.iween.pageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ResultPage extends basePage{

	public ResultPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		
			}
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
