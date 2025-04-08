package config;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class PropertiesFile {
	public static Properties getProperties(String filename) {
		Properties myProperties = new Properties();
		
		try {
			File propertiesFile = new File("src/test/java/config/" + filename + ".properties");
			if(propertiesFile.exists()) {
				myProperties.load(new FileInputStream(propertiesFile));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find file:" + filename);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return myProperties;
	}
}
