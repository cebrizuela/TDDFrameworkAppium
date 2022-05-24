package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qa.BaseTest;

//esta clase se utiliza para el manejo de exepciones 
public class TestListener implements ITestListener {

	public void onTestFailure(ITestResult result) {

		// el metodo getThrowable nos permite leer las excepciones.
		if (result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			// el metodo printStackTrace nos permite imprimir
			result.getThrowable().printStackTrace(pw);

			System.out.println(sw.toString());
		}
		
		// codigo para la captura de pantalla cuando falla
		BaseTest base = new BaseTest();
		File file = base.getDriver().getScreenshotAs(OutputType.FILE);
		
		// Este codigo crea un avariable de tipo Map donde se almacena los parametros definidos en fichero testng.xml
		Map<String, String> params = new HashMap<String,String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		
		System.out.println("Esta es la variable Map de la clase TestListener" + params);		
		
		String imagePath = "Screenshots" + File.separator + params.get("platformName") + "_" + params.get("deviceName") + "_" + File.separator + base.getDataTime()
		+ File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
		
		// codigo para guardar la imagen en un reporte
		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;
		
		
		try {
			FileUtils.copyFile(file, new File(imagePath));
			
			// reporte
			Reporter.log("This is the sample screeshot");
			Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='400' width='400'/> </a>");
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
