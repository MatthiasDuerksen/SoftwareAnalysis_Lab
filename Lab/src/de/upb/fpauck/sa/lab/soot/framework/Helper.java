package de.upb.fpauck.sa.lab.soot.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Helper {
	public static final String SOOT_CLASS_PATH = "sootClassPath";

	private static Properties prop = null;

	public static Properties getProperties() {
		if (prop == null) {
			prop = new Properties();
			try {
				InputStream input = new FileInputStream("soot.properties");
				prop.load(input);
				input.close();
			} catch (IOException ex) {
				System.err.println("Could not load properties file");
			}
		}
		return prop;
	}
}
