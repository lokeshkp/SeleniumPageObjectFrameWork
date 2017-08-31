/**
 * 
 */
package com.ecm.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 
 * @author Shivaprasad Nagaraju
 * @version 1.0 , 25-07-2016
 * 
 */
public enum ApplicationConstants {
	BROWSER, SELENIUM_IMPLICIT_TIMEOUT, SELENIUM_EXPLICIT_TIMEOUT, TELEMATICS_LOGIN_URL,TELEMATICS_WEBCLIENT_LOGIN_URL,TELEMATICS_REPORT_URL,REPORTS_USERNAME,REPORTS_PASSWORD,TELEMATICS_USERNAME, TELEMATICS_PASSWORD,TELEMATICS_WEBCLIENT_USERNAME, TELEMATICS_WEBCLIENT_PASSWORD;

	private static final String PATH = "src/test/resources/properties/application.properties";
	private static Properties properties;

	private String value;

	private void init() {
		if (properties == null) {
			properties = new Properties();
			try {
				FileInputStream fileInputStream = new FileInputStream(PATH);
				properties.load(fileInputStream);
			} catch (Exception e) {
				System.exit(1);
			}
		}
		value = (String) properties.get(this.toString());
	}

	public String getValue() {
		if (value == null) {
			init();
		}
		return value;
	}
}
