package com.qa;

import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

// En esta clase se inicializara el driver 
public class BaseTest {

	protected static AppiumDriver driver;
	protected static Properties props;
	InputStream inputStream; // se utiliza para leer ficheros

	protected static HashMap<String, String> strings = new HashMap<String, String>();
	InputStream inputStrings;
	TestUtils utils;
	
	protected static String platform;

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@org.testng.annotations.Parameters({ "platformName", "udid", "deviceName", "avd" , "emulator", "unlockType", "unlockKey"})
	@BeforeTest
	public void beforeTest(String platformName, String udid, String deviceName, String avd, String emulator, String unlockType, String unlockKey) throws Exception {
		URL url;
		platform = platformName;
		
		try {

			// Codigo para acceder a file de configuracion
			props = new Properties();
			// Codigo para cuando el fichero donde esta la property no se encuentra en el
			// mismo paquete que la clase que lo llama
			String propFileName = "config.properties"; // nombre del fichero donde esta las propiedades
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			// Este codigo permite cargar las propiedades en la variable props
			props.load(inputStream);

			// Codigo para acceder al xml
			String xmlFileName = "strings/strings.xml";
			inputStrings = getClass().getClassLoader().getResourceAsStream(xmlFileName);
			utils = new TestUtils();
			strings = utils.parseStringXML(inputStrings);
			

			DesiredCapabilities desiredCapability = new DesiredCapabilities();
			desiredCapability.setCapability("platforName", platformName);
			desiredCapability.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			

			switch (platformName) {
			case "Android":

				desiredCapability.setCapability(MobileCapabilityType.AUTOMATION_NAME,
						props.getProperty("androidAutomationName"));
				
				// Para iniciar el emulador automaticamente
				if (emulator.equalsIgnoreCase("true")) {
					desiredCapability.setCapability("avd", avd);
					desiredCapability.setCapability("avdLauchTimeout", 118000);
				}
				else { 
					desiredCapability.setCapability("unlockType", unlockType);
					desiredCapability.setCapability("unlockKey", unlockKey);
					desiredCapability.setCapability(MobileCapabilityType.UDID, udid);
					
				}

				// Para instalar la App
				// Para generar la url de la app
				// URL appUrl =
				// getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
				// System.out.println(appUrl);

				String appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
						+ File.separator + "resources" + props.getProperty("androidAppLocation");

				desiredCapability.setCapability(MobileCapabilityType.APP, appUrl);

				// Para abrir la app usamos la opcion appPackage de appActivity
				desiredCapability.setCapability("appPackage", props.getProperty("androidAppPackage"));
				desiredCapability.setCapability("appActivity", props.getProperty("androidAppActivity"));

				url = new URL(props.getProperty("appiumURL"));

				driver = new AndroidDriver(url, desiredCapability);
				break;

			case "iOS":
				/*
				 * desiredCapability.setCapability("automationName",
				 * props.getProperty("iOSAutomationName")); String iOSAppUrl =
				 * System.getProperty("user.dir") + File.separator + "src" + File.separator +
				 * "test" + File.separator + "resources" + File.separator + "app" +
				 * File.separator + "SwagLabsMobileApp.app"; // String iOSAppUrl =
				 * getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
				 * utils.log().info("appUrl is" + iOSAppUrl);
				 * desiredCapability.setCapability("bundleId",
				 * props.getProperty("iOSBundleId"));
				 * desiredCapability.setCapability("wdaLocalPort", wdaLocalPort);
				 * desiredCapability.setCapability("webkitDebugProxyPort",
				 * webkitDebugProxyPort); desiredCapability.setCapability("app", iOSAppUrl);
				 * 
				 * driver = new IOSDriver(url, desiredCapabilities);
				 */
				break;
				
			default:
				throw new Exception("Invalid platform! - " + platformName);
			}

			String sessionId = driver.getSessionId().toString();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			/// Se usa para cerrar los ficheros
			if (inputStream != null) {
				inputStream.close();
			}

			if (inputStrings != null) {
				inputStrings.close();
			}
		}
	}
	
	
/// Los metodos usados en todas las paginas
	public void waitForVisibility(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void clear(MobileElement e) {
		waitForVisibility(e);
		e.clear();
	}
	
	public void click(MobileElement e) {
		waitForVisibility(e);
		e.click();
	}

	public void sendKeys(MobileElement e, String txt) {
		waitForVisibility(e);
		e.sendKeys(txt);
	}

	public String getAttribute(MobileElement e, String attribute) {
		waitForVisibility(e);
		return e.getAttribute(attribute);
	}
	
	public String getText(MobileElement e) {
		switch(platform) {
		case "Android":
			return getAttribute(e,"text");
		case "iOS":
			return getAttribute(e,"label");
		}
		return null;
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
