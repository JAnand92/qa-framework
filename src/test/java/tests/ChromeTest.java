package tests;

import driverFactory.DriverManagerFactory;
import driverFactory.DriverType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChromeTest extends BaseTest {

    @BeforeTest
    private void beforeTest() {
        driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
    }

    @Test
    private void chromeTest() {
        try {
            driver.get("https://www.swtestacademy.com/tag/rest-assured/");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
