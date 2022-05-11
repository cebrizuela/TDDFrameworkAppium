package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.internal.Systematiser;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import javax.xml.soap.Detail;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest{
	
	LoginPage loginPage;
	ProductsPage productsPage;
	
	// variables utilizadas para leer el Json
	InputStream datais;
	JSONObject loginUsers;
	
	@BeforeClass
	public void beforeClass() throws IOException {
		try {
			String dataFileName = "data/loginUser.json";
			System.out.println(dataFileName);
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);	
			JSONTokener tokener = new JSONTokener(datais);
			
			loginUsers = new JSONObject(tokener);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//Se usa para cerrar los ficheros 
			if (datais != null) {
				datais.close();
			}
		}
		
	}

	@AfterClass
	public void afterClass() {
	}


	@BeforeMethod
	public void beforeMethod(Method m) {
		
		
		/// La clase Method se utiliza para obtener el nombre del metodo
		System.out.println("\n" + "****** starting test: " + m.getName() + "*****" + "\n");
		loginPage = new LoginPage();
		
	}

	@AfterMethod
	public void afterMethod() {
	}
	
	 @Test
		public void invalidUserName() {
			loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
			loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
			loginPage.pressLoginButton();

			String actualErrTxt = loginPage.getErrTxt();
			String expectedErrTxt = strings.get("err_invalid_username_or_password");

			System.out.println("actual error txt - " + actualErrTxt + "\n" + "expected error txt - " + expectedErrTxt);
			Assert.assertEquals(actualErrTxt, expectedErrTxt);

		}
	  
	  @Test
	  public void invalidPassword() {
		  loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
			loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		  loginPage.pressLoginButton();
		  
		  String actualErrTxt = loginPage.getErrTxt();
		  String expectedErrTxt = strings.get("err_invalid_username_or_password");
		  
		  System.out.println("actual error txt - " + actualErrTxt + "\n" + "expected error txt - " + expectedErrTxt );
		  
		  Assert.assertEquals(actualErrTxt, expectedErrTxt);
	  }
	  
		
		  @Test public void successfulLogin() {
			  loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
				loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
			  loginPage.pressLoginButton();
			
		  productsPage = new ProductsPage();
		  String actualProductTitle = productsPage.getTitle(); 
		  String expectedProductTitle = strings.get("product_title");
		  System.out.println("actual error txt - " + actualProductTitle + "\n" + "expected error txt - " + expectedProductTitle );
		  
		  Assert.assertEquals(actualProductTitle, expectedProductTitle); }
		 

}
