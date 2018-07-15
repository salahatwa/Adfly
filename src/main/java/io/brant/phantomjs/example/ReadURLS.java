package io.brant.phantomjs.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * @author atwa Jul 15, 2018
 */
@Component
public class ReadURLS {
	@Autowired
	private ResourceLoader resourceLoader;

	public ArrayList<String> getURLS() {

		ArrayList<String> urls = new ArrayList<String>();
		
		try {
			Resource resource = resourceLoader.getResource("classpath:phantomjs/urls.txt");
			File shrinkURLSFILE = resource.getFile();

			FileReader fileReader = new FileReader(shrinkURLSFILE);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				urls.add(line.trim());
			}
			fileReader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return urls;
	}
}
