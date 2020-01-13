package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GSProductPage extends BasePage {

    public GSProductPage(AndroidDriver androidDriver) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
    }

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private static List<WebElement> listOfProductElements;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private static List<WebElement> listOfAddChartElements;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[contains(@resource-id, 'appbar_btn_cart')]")
    private static WebElement btnCart;

    public static void searchForProductAndAddToCart() {
        try {
            /*Appium only see those elements which are visible.. In this case only 2 products are visible which is not true.
             * So, adding all products in list, search for needed product in this list will not work. As, appium will give only visible list items.
             * Now, Only scroll to that product will also not work, as appium will stop as soon as it finds that product. It would be the case, where
             * "Add to Cart" link will not visible...
             * Hence, there is another way of doing that as follows:*/

            androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
                    + ".resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView("
                    + "new UiSelector().text(\"PG 3\"));");

            for(int i = 0;i<listOfProductElements.size();i++) {
                if(listOfProductElements.get(i).getText().trim().equals("PG 3")) {
                    listOfAddChartElements.get(i).click();
                    break;
                }
            }
            btnCart.click();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
