package pages;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import tests.archive.BaseTest;

import java.io.File;
import java.util.Set;

public class BasePage extends BaseTest {

    public static void takeScreenShot(String testCaseName) {
        try {
            File srcFile = ((TakesScreenshot)androidDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(new File("screenshots")
                    .getAbsolutePath(), testCaseName.concat(".png")));
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Switch to Web Context*/
    public static void switchToWebContextAndDoOperations() {
        try {
            Thread.sleep(7000);
            Set<String> contexts = androidDriver.getContextHandles();
            for(String contextName : contexts) {
                System.out.println(contextName);
            }
            androidDriver.context("WEBVIEW_com.androidsample.generalstore");
            androidDriver.findElementByName("q").sendKeys("Hello!");
            /*To search the query on google, press key!*/
            androidDriver.findElementByName("q").sendKeys(Keys.ENTER);
            /*Perform all operation on web view and go back to native app.*/
            androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            Thread.sleep(5000);
            androidDriver.context("NATIVE_APP");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
