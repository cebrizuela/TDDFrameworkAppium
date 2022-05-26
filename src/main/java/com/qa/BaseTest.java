package com.qa;

import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import org.testng.annotations.BeforeTest;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
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

	protected static String dateTime;

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("super before method ");
		((CanRecordScreen) driver).startRecordingScreen();

	}

	// Metodo para terminar la grabacion y guardarla
	@AfterMethod
	public void afterMethod(ITestResult result) {
		String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();

		Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();

		String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName")
				+ File.separator + dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();

		File videoDir = new File(dirPath);

		synchronized (videoDir) {
			if (!videoDir.exists()) {
				videoDir.mkdirs();
			}
		}

		FileOutputStream stream = null;

		 
			try {
				stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
				stream.write(Base64.getDecoder().decode(media));

				stream.write(null);
				stream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	

	@org.testng.annotations.Parameters({ "platformName", "udid", "deviceName", "avd", "emulator", "unlockType",
			"unlockKey" })
	@BeforeTest
	public void beforeTest(String platformName, String udid, String deviceName, String avd, String emulator,
			String unlockType, String unlockKey) throws Exception {

		utils = new TestUtils();
		dateTime = utils.dateTime();
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
				} else {
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

	public AppiumDriver getDriver() {
		return driver;
	}

	// En esta variable se almacena la fecha y hora que iinicia el test
	public String getDataTime() {
		return dateTime;

	}

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
		switch (platform) {
		case "Android":
			return getAttribute(e, "text");
		case "iOS":
			return getAttribute(e, "label");
		}
		return null;
	}

	// metodo para cerrar la app
	public void closeApp() {
		((InteractsWithApps) driver).closeApp();
	}

	// metodo para abrir la app
	public void launchApp() {
		((InteractsWithApps) driver).launchApp();
	}

	// metodo para hacer scroll bucando por elemento-- codigo del profesor
	public MobileElement scrollToElement() {
		return (MobileElement) ((FindsByAndroidUIAutomator) driver)
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
						+ ".scrollable(true)).scrollIntoView(" + "new UiSelector().description(\"test-Price\"));");

	}

	// Metodo para hacer scroll por posicion
	public void scrollByPosition(int startY) {
		TouchAction t = new TouchAction(driver);
		org.openqa.selenium.Dimension size = driver.manage().window().getSize();
		int positionX = size.width / 2;
		int endY = (int) (size.height * 0.2);

		System.out.println("Size " + size + " Y " + size.height + " X " + size.width);
		t.press(PointOption.point(positionX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
				.moveTo(PointOption.point(positionX, endY)).release().perform();

	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
