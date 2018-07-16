package io.brant.phantomjs.example.phantomjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author atwa Jul 15, 2018
 */
@Component
public class Crawler {

	private static Logger logger = LoggerFactory.getLogger(Crawler.class);

	@Value("${screen.shot.path}")
	private String screenShotPath;

	@Value("${screen.shot.available}")
	private boolean isScreenShotAvailable;

	/**
	 * Wait for the page to load, timeout after 30 seconds
	 * 
	 * @param driver
	 */
	public void wait(WebDriver driver) {

		(new WebDriverWait(driver, 35)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.id("skip_bu2tton")).isDisplayed();
			}
		});

	}

	/**
	 * Validate Skip Button Found or not
	 * 
	 * @param element
	 * @return
	 */
	public boolean isValidSkipButton(WebElement element) {
		if (element != null && element.isDisplayed() && element.getAttribute("href") != null) {
			logger.info(">> Original link : {}", element.getAttribute("href"));
			return true;
		} else
			return false;
	}

	/**
	 * Take screen shot
	 * 
	 * @param driver
	 */
	public void screenShot(WebDriver driver) {
		try {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

//			FileUtils.copyFile(file, new File(screenShotPath + "\\" + System.currentTimeMillis() + ".jpeg"), true);
		} catch (Exception ex) {
			logger.error("Error screenshotting , {}", ex.getMessage());
		}
	}

	public void showMyIp() {
		try {
			// Returns the instance of InetAddress containing
			// local host name and address
			InetAddress localhost = InetAddress.getLocalHost();
			logger.info(">> System IP Address : " + (localhost.getHostAddress()).trim());

			// Find public IP address
			String systemipaddress = "";

			URL url_name = new URL("http://bot.whatismyipaddress.com");

			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

			// reads system IPAddress
			systemipaddress = sc.readLine().trim();

			logger.info(">> Public IP Address: " + systemipaddress + "\n");
		} catch (Exception e) {
			logger.error("Cannot Get public ip address Execute Properly");
		}

	}

	/**
	 * Handle Windows
	 * 
	 * @param driver
	 */
	public void terminateWindow(WebDriver driver) {

		try {
			Set<String> windows = driver.getWindowHandles();
			logger.info(">> Number of Windows : {}", windows.size());

			int count = 1;

			String defaultWindow = driver.getWindowHandle();

			for (String window : windows) {

				driver.switchTo().window(window);

				logger.info(">> Window {} Number : {}  , page title :{}", window, count, driver.getTitle());

				// if(isScreenShotAvailable)
				// screenShot(driver);

				if (windows.size() > 1)
					terminate(driver, defaultWindow);

				count++;
			}

		} catch (Exception ex) {
		} finally {
			/*
			 * try { Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe"); } catch
			 * (IOException e) {
			 * logger.info("Failed to Kill all process , {}"+e.getMessage());
			 * e.printStackTrace(); }
			 */
		}
	}

	public void displayCookies(WebDriver driver) {
		Set<Cookie> sessionCookies = driver.manage().getCookies();
		
     // No cookie of the new Session can be found in the cookies of the old Session
        for (Cookie c : sessionCookies) {
            logger.info(">> COOKIES : {}",c);
        }
	}
	
	public void deleteAllCookies(WebDriver driver)
	{
		 driver.manage().deleteAllCookies();
	}

	/**
	 * @param driver
	 */
	public void terminate(WebDriver driver, String defaultWindow) {
		try {
			logger.info("Check if its default window , default : {}   , current window :{}", defaultWindow,
					driver.getWindowHandle());
			if (!driver.getWindowHandle().equalsIgnoreCase(defaultWindow)) {
				logger.info(">> Session ID ", driver.getWindowHandle());

				// driver.close();
			}
		} catch (Exception ex) {
		}

	}
}
