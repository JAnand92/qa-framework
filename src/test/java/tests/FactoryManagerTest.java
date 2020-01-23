package tests;

import driverFactory.DriverManager;
import driverFactory.DriverManagerFactory;
import driverFactory.DriverType;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FactoryManagerTest {
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
    public void launchTest() {
      /*  driver.get("https://www.google.com");
        Assert.assertEquals("Google", driver.getTitle());*/
        System.out.println("Android Testing is ready!");
    }
}
