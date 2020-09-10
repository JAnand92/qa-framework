package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import tests.BaseTest;

public class BasePage extends BaseTest {

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /*Scroll down to the visible element*/
    public void scrollDown(String str) {
        try {
            ((AndroidDriver) driver).findElementByAndroidUIAutomator
                    ("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+str+"\"));");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
    /*Click Method*/
    public void click(WebElement element) {
        try {
            element.click();
            System.out.println("Element Clicked!");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Submit method*/
    public void submit(WebElement element) {
        try {
            element.submit();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Enter value*/
    public void enter(WebElement element, String value) {
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public boolean isAlertPresent() {
        try {
            Wait wait = new WebDriverWait(driver, 610);
            Alert a = (Alert) wait.until(ExpectedConditions.alertIsPresent());
            a.accept();
            return  true;
        } catch (Exception e) {
            return false;
        }
    }

    /*Login to application
    * wait for alert
    * if alert is present switch to it or send exception*/

    public void verifyAlertBoxAfterSessionOut() {
        try {
            /*loginToApplication()*/
            if(isAlertPresent()) {
                System.out.println("Alert box is present after session.");
            } else {
                Assert.fail("Alert box is not present after session.");
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
