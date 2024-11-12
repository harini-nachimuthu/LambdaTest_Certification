package Simpleformdemo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

public class Testscenario_1 {
    
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
    public void test1() {
    	
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.manage().window().maximize();

        WebElement simpleFormDemo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div[1]/div/section[2]/div/ul/li[34]/a")));
        simpleFormDemo.click();

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "simple-form-demo";
        System.out.println(currentUrl.contains(expectedUrl) ? "Yes, URL contains specified text" : "No, this does not contain specified text");

        String variableValue = "Welcome to LambdaTest";
        WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div/div/section[2]/div/div/div/div/div[2]/div/div/input")));
        messageBox.sendKeys(variableValue);

        WebElement checkValue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div/div/section[2]/div/div/div/div/div[2]/div/div/button")));
        checkValue.click();

        WebElement yourMessageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div/div/section[2]/div/div/div/div/div[2]/div/div[2]/div/p")));
        String currentTextValue = yourMessageBox.getText();

        if (variableValue.equals(currentTextValue)) {
            System.out.println("Both texts match - Entered: " + variableValue + " Printed: " + currentTextValue);
        } else {
            System.out.println("Texts do not match - Entered: " + variableValue + " Printed: " + currentTextValue);
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
