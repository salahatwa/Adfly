package io.brant.phantomjs.example;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

import io.brant.phantomjs.example.phantomjs.AdflyCrawler;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
public class ApplicationInitializer implements CommandLineRunner {

	@Autowired
	private AdflyCrawler crawler;

	@Autowired
	private ReadURLS readURLS;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializer.class, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	public void run(String... args) throws Exception {

		ArrayList<String> urls = readURLS.getURLS();

		for (String url : urls) {
			crawler.crawlURL(url);
		}

	}
}
