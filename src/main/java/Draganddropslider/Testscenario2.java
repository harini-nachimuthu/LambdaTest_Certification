package Draganddropslider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Testscenario2 {
	
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
  public void test2() {
	  
      driver.get("https://www.lambdatest.com/selenium-playground");
      driver.manage().window().maximize();
      
      WebElement draganddropsliderbutton=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Drag & Drop Sliders']")));
      draganddropsliderbutton.click();
      
      WebElement draggedvalue=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='slider3']/div/input")));
      
      Actions action=new Actions(driver);
      action.dragAndDropBy(draggedvalue, 188, 0);
      action.perform();
      
      WebElement defaultvalue=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//output[@id='rangeSuccess']")));
      String defaultvalue15text=defaultvalue.getText();
      String Actual_Range = "95";
      
      if (defaultvalue15text.contains(Actual_Range)) {
			System.out.println("Range is matched : "+defaultvalue15text);
		} else {
			System.out.println("Range is not matched! : "+defaultvalue15text);
		}
  }
  
  @AfterMethod
  public void tearDown() {
      driver.executeScript("lambda-status=" + Status);
      driver.quit();
  }
  
}
