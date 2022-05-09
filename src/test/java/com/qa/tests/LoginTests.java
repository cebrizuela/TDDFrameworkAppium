package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.internal.Systematiser;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTests extends BaseTest{
	
	LoginPage loginPage;
	ProductsPage productsPage;

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
	}


	@BeforeMethod
	public void beforeMethod(Method m) {
		
		//productsPage = new ProductsPage();
		
		System.out.println("\n" + "****** starting test: " + m.getName() + "*****" + "\n");
		loginPage = new LoginPage();
		
	}

	@AfterMethod
	public void afterMethod() {
	}
	
	 @Test
	  public void invalidUserName() {
		  loginPage.enterUserName("clara");
		  loginPage.enterPassword("clara");
		  loginPage.pressLoginButton();
		  
		  String actualErrTxt = loginPage.getErrTxt();
		  String expectedErrTxt = "Username and password do not match any user in this service.";
		  
		  System.out.println("actual error txt - " + actualErrTxt + "\n" + "expected error txt - " + expectedErrTxt );
		  
		  Assert.assertEquals(actualErrTxt, expectedErrTxt);
	  }
	  
	  @Test
	  public void invalidPassword() {
		  loginPage.enterUserName("standard_user");
		  loginPage.enterPassword("clara");
		  loginPage.pressLoginButton();
		  
		  String actualErrTxt = loginPage.getErrTxt();
		  String expectedErrTxt = "Username and password do not match any user in this service.";
		  
		  System.out.println("actual error txt - " + actualErrTxt + "\n" + "expected error txt - " + expectedErrTxt );
		  
		  Assert.assertEquals(actualErrTxt, expectedErrTxt);
	  }
	  
		
		  @Test public void successfulLogin() {
			  loginPage.enterUserName("standard_user");
			  loginPage.enterPassword("secret_sauce");
			  loginPage.pressLoginButton();
			
		  productsPage = new ProductsPage();
		  String actualProductTitle = productsPage.getTitle(); 
		  String expectedProductTitle = "PRODUCTS";
		  System.out.println("actual error txt - " + actualProductTitle + "\n" + "expected error txt - " + expectedProductTitle );
		  
		  Assert.assertEquals(actualProductTitle, expectedProductTitle); }
		 

}
