package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class GSHomePage {

    private final WebDriver driver;
    private BasePage basePage;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id, 'nameField')]")
    private static WebElement txtNameField;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[contains(@resource-id, 'radioFemale')]")
    private static WebElement rdbFemale;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'text1')]")
    private static WebElement txtDropDown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text = 'Ireland']")
    private static WebElement txtDropDownOption;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id, 'btnLetsShop')]")
    private static WebElement btnLetsShop;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    private static WebElement txtToastMessage;

    public GSHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        basePage = new BasePage(driver);
    }

    public static GSHomePage using(WebDriver driver) {
        return new GSHomePage(driver);
    }

    public GSHomePage setCountry(String countryName) {
        basePage.click(this.txtDropDown);
        basePage.scrollDown(countryName);
        basePage.click(this.txtDropDownOption);
        return this;
    }

   public GSHomePage setName(String name) {
        basePage.enter(this.txtNameField, "Hello");
        return this;
   }

   public GSHomePage setGender() {
        basePage.click(this.rdbFemale);
        return this;
   }

   public void submit() {
        basePage.click(btnLetsShop);
   }

}
