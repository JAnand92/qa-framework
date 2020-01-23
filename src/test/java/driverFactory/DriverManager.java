package driverFactory;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
    protected WebDriver driver;
    protected abstract void startService();
    protected abstract void stopService();
    protected abstract void createDriver();

    public void quitDriver() {
        try {
            if(null != driver) {
                stopService();
                driver.quit();
                driver = null;
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public WebDriver getDriver() {
        try {
            if(null == driver) {
                startService();
                createDriver();
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return driver;
    }
}
