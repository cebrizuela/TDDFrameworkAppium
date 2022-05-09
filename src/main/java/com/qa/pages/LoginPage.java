package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest{
	
	@AndroidFindBy (accessibility = "test-Username") 
	private MobileElement usernameTxtFld;

	@AndroidFindBy (accessibility = "test-Password") 
	private MobileElement passwordTxtFld;
	
	@AndroidFindBy (accessibility = "test-LOGIN") 
	private MobileElement loginBtn;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") 
	private MobileElement errTxt;

	
	public LoginPage enterUserName(String username) {
		sendKeys(usernameTxtFld, username);
		return this;
	}
	
	
	public LoginPage enterPassword(String password) {
		sendKeys(passwordTxtFld, password);
		return this;
	}
	
	public ProductsPage pressLoginButton() {
		click(loginBtn);
		return new ProductsPage();
	}

	public String getErrTxt() {
		 return getAttribute(errTxt, "text");
	}
}
