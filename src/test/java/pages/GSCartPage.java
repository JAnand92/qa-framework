package pages;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class GSCartPage extends BasePage {

    public GSCartPage(AndroidDriver androidDriver) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private static List<WebElement> listOfProductElementsInCart;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'productPrice')]")
    private static List<WebElement> listOfPriceOfProductInCart;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'totalAmountLbl')]")
    private static WebElement totalPrice;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private static WebElement notificationElement;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id, 'termsButton')]")
    private static WebElement termCondition;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id, 'button1')]")
    private static WebElement btnOk;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@resource-id, 'btnProceed')]")
    private static WebElement btnProceed;

    public static void viewAndValidateCartItemsAndProceed() {
        try {
            for(int i = 0; i<listOfProductElementsInCart.size(); i++) {
                if(listOfProductElementsInCart.get(i).getText().trim().equals("PG 3")) {
                    System.out.println("Expected product(s) added in the cart!");
                } else {
                    System.out.println("Expected product(s) not added in the cart!");
                }
            }
            Double prices =0.0d;
            for(WebElement ae : listOfPriceOfProductInCart) {
                prices = prices + Double.valueOf(ae.getText().substring(1).trim());
            }

            if(Double.valueOf(totalPrice.getText().substring(1).trim()).equals(prices)) {
                System.out.println("Total purchase amount is equal to total product's price!");
            } else {
                System.out.println("Total purchase amount is not equal to total product's price!");
            }

            TouchAction touchAction = new TouchAction(androidDriver);
            touchAction.tap(TapOptions.tapOptions().withElement(ElementOption.element(notificationElement))).perform();

            touchAction.longPress(LongPressOptions.longPressOptions().withElement
                    (ElementOption.element(termCondition)).withDuration(Duration.ofSeconds(2))).perform();

            btnOk.click();
            btnProceed.click();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }



}
