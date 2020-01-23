package tests;

import driverFactory.DriverManagerFactory;
import driverFactory.DriverType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.GSHomePage;

public class AndroidGSTest extends BaseTest {

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerFactory.getManager(DriverType.ANDROID);
    }

    @Test
    private void TC_ClientJourney() {
        try {
            GSHomePage.using(driver)
                    .setCountry("Ireland")
                    .setGender()
                    .setName("Name")
                    .submit();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
