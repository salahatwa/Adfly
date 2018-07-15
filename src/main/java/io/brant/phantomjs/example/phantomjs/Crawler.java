package io.brant.phantomjs.example.phantomjs;

import java.io.File;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
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

			FileUtils.copyFile(file, new File(screenShotPath+"\\" +System.currentTimeMillis() + ".jpeg"), true);
		} catch (Exception ex) {
			logger.error("Error screenshotting , {}", ex.getMessage());
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

				if(isScreenShotAvailable)
				screenShot(driver);

				terminate(driver, defaultWindow);

				count++;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			/*
			 * try { Runtime.getRuntime().exec("taskkill /F /IM phantomjs.exe"); } catch
			 * (IOException e) {
			 * logger.info("Failed to Kill all process , {}"+e.getMessage());
			 * e.printStackTrace(); }
			 */
		}
	}

	/**
	 * @param driver
	 */
	public void terminate(WebDriver driver, String defaultWindow) {
		try {
			if (!driver.getWindowHandle().equalsIgnoreCase(defaultWindow)) {
				logger.info(">> Session ID ", driver.getWindowHandle());

				driver.close();
			}
		} catch (Exception ex) {
		}

	}
}
