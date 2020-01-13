package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class GSHomePage extends BasePage{

    public GSHomePage(AndroidDriver androidDriver) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
    }

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id, 'nameField')]")
    private static WebElement txtNameField;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[contains(@resource-id, 'radioFemale')]")
    private static WebElement rdbFemale;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'text1')]")
    private static WebElement txtDropDown;

    @AndroidFindBy(uiAutomator = "//android.widget.TextView[@text = 'Ireland']")
    private static WebElement txtDropDownOption;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id, 'btnLetsShop')]")
    private static WebElement btnLetsShop;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    private static WebElement txtToastMessage;

    public static void fillFormAndStartShopping() {
        try {
            rdbFemale.click();
            txtDropDown.click();
            androidDriver.findElementByAndroidUIAutomator
                    ("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Ireland\"));");

            /*In case scroll into view does not work on some android OS version(s)*/
            /* driver.findElement(MobileBy.AndroidUIAutomator
            ("new UiScrollable(new UiSelector().scrollable(true).instance(0))
            .scrollIntoView(new UiSelector().
            extMatches(\"" + containedText + "\").instance(0))"));  */

            txtDropDown.click();
            btnLetsShop.click();

            /*In case Toast messages(Validation/Error Message) --> Class Name would be always android.widget.Toast
             * and, error message is always put into name attribute.*/
            String toastMessage = txtToastMessage.getAttribute("name");
            if(toastMessage.contains("Please")) {
                System.out.println("Error Message: " + toastMessage);
                txtNameField.sendKeys("Testing");
                btnLetsShop.click();
            }

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
