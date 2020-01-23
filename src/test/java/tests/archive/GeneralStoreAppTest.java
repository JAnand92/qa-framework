package tests.archive;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.archive.GSCartPage;
import pages.GSHomePage;
import pages.archive.GSProductPage;

public class GeneralStoreAppTest extends BaseTest {
    private static String generalStoreApk = "General-Store.apk";
    private static String emulatorName = "JATestEmulator";

    private static GSHomePage gsHomePage = null;
    private static GSProductPage gsProductPage = null;
    private static GSCartPage gsCartPage = null;

    @BeforeClass
    public void beforeClass() {
        try {
            setupAppium(generalStoreApk, emulatorName);
            gsHomePage = new GSHomePage(androidDriver);
            gsProductPage = new GSProductPage(androidDriver);
            gsCartPage = new GSCartPage(androidDriver);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }



    @Test
    private void TC_001_ClientItemPurchaseJourney() {
        try {
            GSHomePage.fillFormAndStartShopping();
            GSProductPage.searchForProductAndAddToCart();
            GSCartPage.viewAndValidateCartItemsAndProceed();
            BasePage.switchToWebContextAndDoOperations();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
