package utilis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	static Properties value = new Properties();

	public static Properties intializeProperties() {
		FileInputStream file = null;
		try {
			file = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\config\\data.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			value.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getBaseApiURI(String key) {
		if (value == null) {
			intializeProperties();
		}
		return value.getProperty(key);
	}
}
