package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends BaseTest {
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private MobileElement productTitle;
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private MobileElement productDescription;
	
	@AndroidFindBy (accessibility =  "test-Price")
	private MobileElement productPrice;
	
	@AndroidFindBy (accessibility = "test-ADD TO CART")
	private MobileElement buttonAddCart;
	
	@AndroidFindBy (accessibility =  "test-BACK TO PRODUCTS")
	private MobileElement backToProductsBtn;
	
	
	public String getProductTitleText() {
		String productTitleText = getText(productTitle);
		System.out.println("The product title is " + productTitleText);
		return productTitleText; 
	}
	
	public String getProductDescriptionText() {
		String productDescriptionText = getText(productDescription);
		System.out.println("The product description is " + productDescriptionText);
		return productDescriptionText; 
	}
	
	public String getProductPriceText() {
		String productPriceText = getText(productPrice);
		System.out.println("The product price is " + productPriceText);
		return productPriceText;
	}

	public Boolean isButtonAddCartIsDisplayed(){ 
		System.out.println("The AddCart button is displayed " );
		return buttonAddCart.isDisplayed();
		
		}
	
	public ProductsPage pressBackToProductsBtn() {
		System.out.println("Press the button BackToProduct ");
		click(backToProductsBtn);
		return new ProductsPage();
	}
}
