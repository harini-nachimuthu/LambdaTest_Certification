package Inputformsubmit;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;

public class Testscenario3 {
	
	private RemoteWebDriver driver;
    private String Status = "passed";
    private WebDriverWait wait;

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "harini.nachimuthu" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "p1wVUJdIojrr2A8AqeoJPUE1a3P3faDLzZYwBp6f2bsFyGgHZE" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        // Retrieve browser, version, and platform from testng.xml parameters
        String browserName = ctx.getCurrentXmlTest().getParameter("browser");
        String browserVersion = ctx.getCurrentXmlTest().getParameter("version");
        String platformName = ctx.getCurrentXmlTest().getParameter("platform");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", platformName);
        caps.setCapability("browserName", browserName);
        caps.setCapability("browserVersion", browserVersion);

        // LambdaTest-specific capabilities
        caps.setCapability("lt:options", Map.of(
            "build", "TestNG With Java",
            "name", m.getName() + this.getClass().getName(),
            "resolution", "1024x768"
        ));

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
        wait = new WebDriverWait(driver, Duration.ofSeconds(35)); // Initialize WebDriverWait with 35 seconds timeout
    }

	
    @Test
    public void test3() {
        driver.manage().deleteAllCookies();
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.manage().window().maximize();

        WebElement inputFormSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Input Form Submit')]")));
        inputFormSubmit.click();

        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Submit']")));
        submitBtn.click();

        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='inputEmail4']")));
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='inputPassword4']")));
        WebElement company = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='company']")));
        WebElement website = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='websitename']")));
        Select selectCountry = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='country']"))));

        name.sendKeys("Lambda Test Assessment");
        email.sendKeys("Lambda.Test@gmail.com");
        password.sendKeys("Lambda!246810");
        company.sendKeys("LambdaTest company");
        website.sendKeys("www.LambdaTest.com");
        selectCountry.selectByValue("IN");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='success-msg hidden']"))).getText();
        
        WebElement address1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='inputAddress1']")));
        WebElement address2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='inputAddress2']")));
        WebElement state = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='inputState']")));
        WebElement zipcode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='inputZip']")));

        
        address1.sendKeys("No 1 Lambda Street ");
        address2.sendKeys("Lambda Test City");
        state.sendKeys("Lambda Test State");
        zipcode.sendKeys("246810");
        
        submitBtn.click();
        
        String actualmsg=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='success-msg hidden']"))).getText();
        String expectedmsg="Thanks for contacting us, we will get back to you shortly.";
        
        if(actualmsg.equals(expectedmsg)) {
        	System.out.println("Message matched and sucessfull login... ");
        }else {
        	System.out.println("Message does not match and unsucessfull login... ");
        }
    }
  
  @AfterMethod
  public void tearDown() {
      driver.executeScript("lambda-status=" + Status);
      driver.quit();
  }

}
