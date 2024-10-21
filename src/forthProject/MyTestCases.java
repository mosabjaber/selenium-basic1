package forthProject;

import java.sql.Array;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class MyTestCases {

	WebDriver driver = new ChromeDriver();

	String myWebsite = "https://automationteststore.com/";
	String[] firstNames = { "ahmad", "ali", "anas", "omar", "ayat", "alaa", "sawsan", "Rama" };
	String[] LastNames = { "Khaled", "mustafa", "Mohammad", "abdullah", "malek", "omar" };
	String UserFirstName = "";
	String userNameLogin = "";
	String Password = "mynameIsMosab$";

	Random rand = new Random();

	@BeforeTest
	public void mySetup() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get(myWebsite);

	}

	@Test(priority = 1, enabled = false)
	public void signUp() throws InterruptedException {

		int RandomIndexForTheFirstName = rand.nextInt(firstNames.length);
		int RandomIndexForTheLastName = rand.nextInt(LastNames.length);

		UserFirstName = firstNames[RandomIndexForTheFirstName];
		String UserLastName = LastNames[RandomIndexForTheLastName];

		int randomNumberForTheEmail = rand.nextInt(564548);

		String domainName = "@gmail.com";

		userNameLogin = UserFirstName + UserLastName + randomNumberForTheEmail;

		driver.findElement(By.linkText("Login or register")).click();

		WebElement SignUpButton = driver.findElement(By.xpath("//button[@title='Continue']"));

		SignUpButton.click();

		Thread.sleep(2000);

		WebElement FirstNameInput = driver.findElement(By.id("AccountFrm_firstname"));
		FirstNameInput.sendKeys(UserFirstName);
		WebElement LastNameInput = driver.findElement(By.id("AccountFrm_lastname"));
		LastNameInput.sendKeys(UserLastName);
		WebElement EmailInput = driver.findElement(By.id("AccountFrm_email"));
		EmailInput.sendKeys(userNameLogin + domainName);
		WebElement AdressInput = driver.findElement(By.id("AccountFrm_address_1"));
		AdressInput.sendKeys("amman city - tlaa al ali");
		WebElement CityInput = driver.findElement(By.id("AccountFrm_city"));
		CityInput.sendKeys("capital city");

		WebElement CountryInput = driver.findElement(By.id("AccountFrm_country_id"));

		Select selector2 = new Select(CountryInput);

		int randomCountry = rand.nextInt(1, 240);

		selector2.selectByIndex(randomCountry);

		Thread.sleep(3000);

		WebElement ZoneIdInput = driver.findElement(By.id("AccountFrm_zone_id"));
		Select selector = new Select(ZoneIdInput);
		int randomState = rand.nextInt(1, 6);

		selector.selectByIndex(randomState);

		WebElement PostalCodeInput = driver.findElement(By.id("AccountFrm_postcode"));
		PostalCodeInput.sendKeys("13310");
		WebElement LoginNameInput = driver.findElement(By.id("AccountFrm_loginname"));

		LoginNameInput.sendKeys(UserFirstName + UserLastName + randomNumberForTheEmail);
		WebElement PasswordInput = driver.findElement(By.id("AccountFrm_password"));
		PasswordInput.sendKeys(Password);
		WebElement ConfirmPasswordInput = driver.findElement(By.id("AccountFrm_confirm"));
		ConfirmPasswordInput.sendKeys(Password);

		WebElement AgreeCheckBox = driver.findElement(By.id("AccountFrm_agree"));
		AgreeCheckBox.click();

		WebElement ContinueButton = driver.findElement(By.xpath("//button[@title='Continue']"));

		ContinueButton.click();

	}

	@Test(priority = 2, enabled = false)

	public void Logout() throws InterruptedException {

		Thread.sleep(2000);
		WebElement UserNave = driver.findElement(By.id("customernav"));
		Actions action = new Actions(driver);
		action.moveToElement(UserNave).perform();
		driver.findElement(By.linkText("Not " + UserFirstName + "? Logoff")).click();

	}

	@Test(priority = 3, enabled = false)
	public void login() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.linkText("Login or register")).click();
		Thread.sleep(2000);
		WebElement LoginInput = driver.findElement(By.id("loginFrm_loginname"));
		LoginInput.sendKeys(userNameLogin);

		WebElement PasswordInput = driver.findElement(By.id("loginFrm_password"));

		PasswordInput.sendKeys(Password);

		WebElement LoginButton = driver.findElement(By.xpath("//button[@title='Login']"));

		LoginButton.click();
	}

	@Test(priority = 4)
	public void AddItemToThecart() throws InterruptedException {
		Thread.sleep(2000);
		int randomWebSitesForTheItems = rand.nextInt(2, 9);
		WebElement WebSitesForTheItems = driver
				.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[" + randomWebSitesForTheItems + "]/a"));
		// System.out.println(randomWebSitesForTheItems +
		// WebSitesForTheItems.getText());
		WebSitesForTheItems.click();

		// ......................................................................
		WebElement listOfItem = driver.findElement(By.cssSelector(".thumbnails.row"));
		int totalNumberOfItems = listOfItem.findElements(By.tagName("li")).size();
		int randomIdexForTheItem = rand.nextInt(totalNumberOfItems);
		Thread.sleep(2000);
		listOfItem.findElements(By.tagName("li")).get(randomIdexForTheItem).click();

		// ..............................................................................
		WebElement subListOfItem = driver.findElement(By.cssSelector(".thumbnails.grid.row.list-inline"));
		int numberOfProduct = subListOfItem.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).size();
		int randomIndexForTheproduct = rand.nextInt(numberOfProduct);
		subListOfItem.findElements(By.cssSelector(".col-md-3.col-sm-6.col-xs-12")).get(randomIndexForTheproduct)
				.click();

		Thread.sleep(4000);
		// ..............................................................................

		WebElement ULList = driver.findElement(By.className("productpagecart"));
		int LiItem = ULList.findElements(By.tagName("li")).get(0).findElements(By.tagName("a")).size();

		if (LiItem > 0) {
			driver.findElement(By.className("cart")).click();
			
			Thread.sleep(2000);
			String ActualResult = driver.findElement(By.className("heading1")).getText();
			String ExpectedResult = "Shopping Cart";

			Assert.assertEquals(ActualResult, ExpectedResult.toUpperCase());
			boolean ExpectedValueForCheckOut = true;
			boolean ActualValueForCheckOut = driver.findElement(By.id("cart_checkout1")).isDisplayed();
			Assert.assertEquals(ActualValueForCheckOut, ExpectedValueForCheckOut, "soso hi");
			

		} else {
			driver.get(myWebsite);
	//		System.out.println("sorry the item out of the stock ");
			
			String ExpectedResult = "https://automationteststore.com/";
			String ActualResult = driver.getCurrentUrl();
			Assert.assertEquals(ActualResult, ExpectedResult, "sosso");


		}

	}

}
