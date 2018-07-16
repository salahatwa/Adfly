package io.brant.phantomjs.example;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author atwa Jul 15, 2018
 */

@Configuration
@PropertySource("classpath:phantomjs/phantomjs.properties")
public class PhantomJsConf {

	@Value("${phantomjs.driver.path}")
	private String driverPath ;
	
	final String chromeBinary = "C:/Users/Elias/AppData/Local/Google/Chrome/Application/chrome.exe";
	
	@Value("${chrome.driver}")
	private String webdriverChromeBinary ;
	
	
	public WebDriver firefoxDriver(WebDriver driver) {
		driver = new FirefoxDriver();
		return driver;
	}

	@Bean
	public WebDriver driver() {
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

//		desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath);
		desiredCapabilities.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
		desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		desiredCapabilities.setCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY, true);
		desiredCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);

		desiredCapabilities.setJavascriptEnabled(true);

		ArrayList<String> cliArgs = new ArrayList<String>();
		cliArgs.add("--web-security=true");
		cliArgs.add("--ignore-ssl-errors=true");
		cliArgs.add("--webdriver-loglevel=DEBUG");
		cliArgs.add("--webdriver-loglevel=NONE");
		desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgs);
		

		desiredCapabilities = DesiredCapabilities.chrome();
		desiredCapabilities.setJavascriptEnabled(true);
//		desiredCapabilities.setCapability("chrome.binary", chromeBinary);
		desiredCapabilities.setCapability("newCommandTimeout", 90); //or seconds
		System.setProperty("webdriver.chrome.driver", webdriverChromeBinary); 

		WebDriver driver = new ChromeDriver(desiredCapabilities);
		

		return driver;

	}
}
