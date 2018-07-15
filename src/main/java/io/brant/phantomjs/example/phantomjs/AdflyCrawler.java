package io.brant.phantomjs.example.phantomjs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdflyCrawler {

	private static Logger logger = LoggerFactory.getLogger(AdflyCrawler.class);

	@Autowired
	private WebDriver driver;

	@Autowired
	private Crawler crawler;

	@Value("${crawler.number}")
	private int crawlerNumber;

	public void crawlURL(String url) {
		try {

			for (int i = 1; i <= crawlerNumber; i++) {
				
				logger.info("--------------------------Excute Number [\"{}\"]  For URL : {}------------------------------",i,url);
				
				excute(url);

				logger.info("---------------------------------------------------------");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method take url , excute crawler
	 * 
	 * @param url
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void excute(String url) throws IOException, InterruptedException {

		driver.get(url);

		logger.info(">> Page title is: {}", driver.getTitle());

		crawler.wait(driver);

		// Find the text input element by its name
		WebElement element = driver.findElement(By.id("skip_bu2tton"));

		if (crawler.isValidSkipButton(element)) {

			element.click();

			crawler.terminateWindow(driver);
		}

	}

	public void wait(WebDriver driver) {
		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());
	}

	public void link(WebDriver driver) {
		// Grab all the anchor tags on the page you're currently on.
		List<WebElement> anchors = driver.findElements(By.tagName("a"));

		// Create a 2nd List to hold the URLs of the anchor tags.
		List<String> allURLs = new ArrayList<String>();

		// Iterate through all the anchors that you got.
		for (WebElement a : anchors) {

			// Print out the URL of the anchor.
			System.out.println(a.getAttribute("href"));

			// Store the URL of the List.
			allURLs.add(a.getAttribute("href"));
		}
	}
}
