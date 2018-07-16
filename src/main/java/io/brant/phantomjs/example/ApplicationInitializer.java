package io.brant.phantomjs.example;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.brant.phantomjs.example.phantomjs.AdflyCrawler;

@SpringBootApplication
public class ApplicationInitializer implements CommandLineRunner {

	@Autowired
	private AdflyCrawler crawler;

	@Autowired
	private ReadURLS readURLS;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationInitializer.class, args);
	}

	/*
	 * 95.211.171.172
	 * 95.211.171.169
	 * 109.201.137.42
	 * 81.171.26.8
	 * 95.211.101.196
	 * 81.171.26.8
	 * (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	public void run(String... args) throws Exception {

		ArrayList<String> urls = readURLS.getURLS();

		for (String url : urls) {
			crawler.crawlURL(url);
		}

	}
}


