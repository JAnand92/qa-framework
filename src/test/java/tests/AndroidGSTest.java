package tests;

import driverFactory.DriverManager;
import driverFactory.DriverManagerFactory;
import driverFactory.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.GSHomePage;

public class AndroidGSTest {

    private DriverManager driverManager;
    private WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerFactory.getManager(DriverType.ANDROID);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }

    @Test
    private void TC_ClientJourney() {
        try {
            GSHomePage.using(driver)
                    .setGender()
                    .setName("Name")
                    .submit();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
