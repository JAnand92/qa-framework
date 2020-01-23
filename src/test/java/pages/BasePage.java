package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
}
