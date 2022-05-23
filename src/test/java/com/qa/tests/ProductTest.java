package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.internal.Systematiser;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ProductTest extends BaseTest {

	LoginPage loginPage;
	ProductsPage productsPage;
	ProductDetailsPage productDetailsPage;
	SettingsPage settingPage;

	// variables utilizadas para leer el Json
	InputStream datais;
	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws IOException {
		try {
			// Para cargar los datos del json
			String dataFileName = "data/loginUser.json";
			System.out.println(dataFileName);
			// en details se carga los valores del json
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			// con este codigo el Jsontoker usado despues para crear el objeto json
			JSONTokener tokener = new JSONTokener(datais);

			loginUsers = new JSONObject(tokener);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Se usa para cerrar los ficheros
			if (datais != null) {
				datais.close();
			}
		}

		closeApp();
		launchApp();

	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeMethod
	public void beforeMethod(Method m) {

		/// La clase Method se utiliza para obtener el nombre del metodo
		System.out.println("\n" + "****** starting test: " + m.getName() + "*****" + "\n");

		loginPage = new LoginPage();
		productsPage = loginPage.login(loginUsers.getJSONObject("validUser").getString("username"),
				loginUsers.getJSONObject("validUser").getString("password"));

	}

	@AfterMethod
	public void afterMethod() {
		settingPage = productsPage.pressSettingsBtn();
		loginPage = settingPage.pressLogoutBtn();
	}
	

	@Test
	public void validateProductOnProductPage() {

		SoftAssert sa = new SoftAssert();

	

		String actualProductTitle = productsPage.getProductTitleText();
		String expectedProductTitle = strings.get("products_page_slb_title");
		System.out.println("actual product title txt - " + actualProductTitle + "\n" + "expected product title txt - "
				+ expectedProductTitle);
		sa.assertEquals(actualProductTitle, expectedProductTitle);

		String actualProductPrice = productsPage.getProductPriceText();
		String expectedProductPrice = strings.get("products_page_slb_price");
		System.out.println("actual product price txt - " + actualProductPrice + "\n" + "expected product price txt - "
				+ expectedProductPrice);
		sa.assertEquals(actualProductPrice, expectedProductPrice);

	

		sa.assertAll();
	}

	@Test
	public void validateProductOnProductDetails() {

		SoftAssert sa = new SoftAssert();


		productDetailsPage = productsPage.pressProductTitle();

		String actualProductTitle = productDetailsPage.getProductTitleText();
		String expectedProductTitle = strings.get("product_details_page_slb_title");
		System.out.println("actual product title txt - " + actualProductTitle + "\n" + "expected product title txt - "
				+ expectedProductTitle);
		sa.assertEquals(actualProductTitle, expectedProductTitle);

		String actualProductDescription = productDetailsPage.getProductDescriptionText();
		String expectedProductDescription = strings.get("product_details_page_slb_txt");
		System.out.println("actual product details txt - " + actualProductDescription + "\n"
				+ "expected product details txt - " + expectedProductDescription);
		sa.assertEquals(actualProductDescription, expectedProductDescription);

		/*
		 * String actualProductPrice = productDetailsPage.getProductPriceText(); String
		 * expectedProductPrice = strings.get("product_details_page_slb_price");
		 * System.out.println("actual product price txt - " + actualProductPrice + "\n"
		 * + "expected product price txt - " + expectedProductPrice);
		 * sa.assertEquals(actualProductPrice, expectedProductPrice);
		 * 
		 * System.out.println("Is displayed the ADD Cart button " +
		 * productDetailsPage.isButtonAddCartIsDisplayed());
		 * sa.assertTrue(productDetailsPage.isButtonAddCartIsDisplayed());
		 */

		productsPage = productDetailsPage.pressBackToProductsBtn();

		sa.assertAll();
	}

}
