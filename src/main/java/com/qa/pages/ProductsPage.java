package com.qa.pages;

import com.qa.BaseTest;
import com.qa.MenuPage;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsPage extends MenuPage {

	@AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Toggle\"]/parent::*[1]/preceding-sibling::*[1]")
	private MobileElement productTitleTxt;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private MobileElement productTitle;
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private MobileElement productPrice;

	public String getTitle() {
		String productsTitle = getText(productTitleTxt);
		System.out.println("Products list title is " + productsTitle);
		return productsTitle;

	}

	public String getProductTitleText() {
		String productTitleText = getText(productTitle);
		System.out.println("Product title is " + productTitleText);
		return productTitleText;

	}

	public String getProductPriceText() {
		String productPriceText = getText(productPrice);
		System.out.println("Product price is " + productPriceText);
		return productPriceText;
	}

	public ProductDetailsPage pressProductTitle() {
		System.out.println("Press product tittle ");
		click(productTitle);
		return new ProductDetailsPage();
	}
}
