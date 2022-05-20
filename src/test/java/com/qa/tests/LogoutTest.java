package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.MenuPage;
import com.qa.pages.LoginPage;
import com.qa.pages.SettingsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LogoutTest extends BaseTest {
  MenuPage menuPage;
  SettingsPage settingPage;
  LoginPage loginPage;
	

  @BeforeMethod
  public void beforeMethod(Method m) {
	/// La clase Method se utiliza para obtener el nombre del metodo
			System.out.println("\n" + "****** starting test: " + m.getName() + "*****" + "\n");
			
			menuPage = new MenuPage();
  }


  @AfterMethod
  public void afterMethod() {
  }

  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }
  
  @Test
  public void successfulLogout() {
	  menuPage.pressSettingsBtn();
	  settingPage = new SettingsPage();
	  settingPage.pressLogoutBtn();
	  loginPage = new LoginPage();
	  
	  String actualloginTitle = loginPage.getUserName(); 
	  String expectedLoginTitle = strings.get("login_app");
	  System.out.println("actual error txt - " + actualloginTitle + "\n" + "expected error txt - " + expectedLoginTitle );
	  
	  Assert.assertEquals(actualloginTitle, expectedLoginTitle); 
	  }
  }


