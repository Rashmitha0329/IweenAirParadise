package com.iween.testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.iween.utilities.EmailUtils;
import com.iween.utilities.ExtentManager;
import com.iween.utilities.Iween_FutureDates;
import com.iween.utilities.Log;
import com.iween.utilities.ReportUtils;
import com.iween.utilities.ScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.lang.reflect.Method;

public class baseClass {

	/*
	 public static WebDriver driver;
	    public static Logger logger;
	    public static Properties p;
	    public static ExtentTest test;
	    public Log log;
	    public ScreenshotUtil screenShots;
	    public ExtentReports extent;
	    public Iween_FutureDates futureDates;
*/
	

	    protected WebDriver driver;
	    protected Logger logger;
	    protected Properties p;
	    protected ExtentTest test;
	    protected Log log;
	    protected ScreenshotUtil screenShots;
	    protected ExtentReports extent;
	    protected Iween_FutureDates futureDates;

	   
	    
//    @BeforeMethod
//    @Parameters({"os", "browser"})
//    public void setup(String os, String browser,Method method) throws Exception {
//        // Initialize Logger
//        logger = LogManager.getLogger(this.getClass());
//
//        // Load properties
//        p = new Properties();
//        FileReader file = new FileReader("./src/test/resources/config.properties");
//        p.load(file);
//
//        logger.info("Operating System: " + os);
//        logger.info("Browser: " + browser);
//
//        // Setup WebDriver
//        switch (browser.toLowerCase()) {
//            case "chrome":
//                WebDriverManager.chromedriver().setup();
//                driver = new ChromeDriver();
//                break;
//            case "firefox":
//                WebDriverManager.firefoxdriver().setup();
//                driver = new FirefoxDriver();
//                break;
//            case "edge":
//                WebDriverManager.edgedriver().setup();
//                driver = new EdgeDriver();
//                break;
//            default:
//                throw new IllegalArgumentException("Unsupported browser: " + browser);
//        }
//
//        driver.manage().deleteAllCookies();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//        driver.manage().window().maximize();
//        
//        driver.get(p.getProperty("applicationUrl"));
//
//        logger.info("Browser launched and navigated to URL: " + p.getProperty("applicationUrl"));
//
//        // Setup Extent Reporting properly
//        extent = ExtentManager.getExtentReports();
//     //   test = extent.createTest(method.getName());
//        ExtentManager.setTest(test); // ✅ Now test won't be null
//        
//        
//
//        // Setup log and screenshot util
//        log = new Log(); // Uses ExtentManager.getTest()
//        screenShots = new ScreenshotUtil();
//
//      futureDates =new  Iween_FutureDates();
//      
//       
//        
//        
//    }
    
	    @BeforeMethod
	    @Parameters({"os", "browser"})
	    public void setup(String os, String browser, Method method) throws Exception {
	        // Initialize Logger
	        logger = LogManager.getLogger(this.getClass());

	        // Load properties
	        p = new Properties();
	        FileReader file = new FileReader("./src/test/resources/config.properties");
	       // \src\test\resources\config.properties
	        p.load(file);

	        logger.info("Operating System: " + os);
	        logger.info("Browser: " + browser);

	        // Setup WebDriver
	        switch (browser.toLowerCase()) {
	        case "chrome":
	        	 WebDriverManager.chromedriver().setup();

	             ChromeOptions options = new ChromeOptions();

	             // Disable Chrome password manager and notifications
	             Map<String, Object> prefs = new HashMap<>();
	             prefs.put("credentials_enable_service", false);
	             prefs.put("profile.password_manager_enabled", false);
	             prefs.put("profile.default_content_setting_values.notifications", 2);  // Block notifications
	             prefs.put("profile.default_content_setting_values.popups", 0);        // Block popups

	             options.setExperimentalOption("prefs", prefs);

	             // Add command line switches to disable save password bubbles, notifications, infobars, password generation etc
	             options.addArguments(
	                 "--disable-notifications",
	                 "--disable-save-password-bubble",
	                 "--disable-infobars",
	                 "--disable-password-manager-reauthentication",
	                 "--disable-password-generation",
	                 "--no-default-browser-check",
	                 "--disable-popup-blocking",
	                 "--disable-features=PasswordManagerSettingsLeakDetection,PasswordManagerEnabled",
	                 "--disable-credential-saving-prompt",
	                 "--disable-blink-features=BlockCredentialedSubresources"
	             );

	             // Use a temporary fresh user data directory (very important!)
	             String tempProfilePath = System.getProperty("java.io.tmpdir") + "/chrome_profile_" + System.currentTimeMillis();
	             options.addArguments("user-data-dir=" + tempProfilePath);

	             driver = new ChromeDriver(options);
	            break;

	       
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                driver = new FirefoxDriver();
	                break;

	            case "edge":
	                WebDriverManager.edgedriver().setup();
	                driver = new EdgeDriver();
	                break;

	            default:
	                throw new IllegalArgumentException("Unsupported browser: " + browser);
	        }

	        driver.manage().deleteAllCookies();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	        driver.manage().window().maximize();

	        driver.get(p.getProperty("applicationUrl"));

	        logger.info("Browser launched and navigated to URL: " + p.getProperty("applicationUrl"));

	        // Setup Extent Reporting properly
	        extent = ExtentManager.getExtentReports();
	        // test = extent.createTest(method.getName());
	        ExtentManager.setTest(test); // ✅ Now test won't be null

	        // Setup log and screenshot util
	        log = new Log(); // Uses ExtentManager.getTest()
	        screenShots = new ScreenshotUtil();

	        futureDates = new Iween_FutureDates();
	    }



    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing browser.");
           // driver.quit();
        }
    }
    
    /*
    @AfterSuite //enable this when ever u wnat to send to email
    public void afterSuite() {
        String reportsFolder = "C:/Users/LENOVO/Downloads/iween-main/iween-main/reports";
        String latestReport = ReportUtils.getLatestReportPath(reportsFolder);

        if (latestReport != null) {
            System.out.println("Sending report: " + latestReport);
            EmailUtils.sendEmailWithAttachment(latestReport);
        } else {
            System.out.println("No report file found in folder: " + reportsFolder);
        }
    }
    */
}
