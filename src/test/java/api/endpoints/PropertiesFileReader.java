package api.endpoints;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {

	public FileReader reader;
	Properties pr;

	public PropertiesFileReader() throws IOException {

		reader = new FileReader("src/test/resources/urls.properties");
		pr = new Properties();
		pr.load(reader);
	}

	public String getUrl(String key) {
		return pr.getProperty(key);
	}

}
