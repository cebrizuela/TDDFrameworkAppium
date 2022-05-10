package com.qa.listeners;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestListener;
import org.testng.ITestResult;

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

	}
}
