package driverFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.helpers.PropertyReaderHelper;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidDriverManager extends DriverManager {

    private AppiumDriverLocalService appDriverService;

    /*File path where mobile application kept kept.*/
    private File fileSrc = new File(PropertyReaderHelper.getProperties("RESOURCE_FILE_PATH"));

    @Override
    protected void startService() {
        try {
        Runtime.getRuntime().exec(PropertyReaderHelper.getProperties("NODE_EXE"));
        if(null == appDriverService) {
                appDriverService = AppiumDriverLocalService.buildDefaultService();
                appDriverService.start();
                startDevice();
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Override
    protected void stopService() {
        if(null != appDriverService && appDriverService.isRunning()) {
            try {
                appDriverService.stop();
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }
    }

    @Override
    protected void createDriver() {
        try {
            File filePath = new File(fileSrc, PropertyReaderHelper.getProperties("APP_NAME"));
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, PropertyReaderHelper.getProperties("DEVICE_NAME"));
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, PropertyReaderHelper.getProperties("AUTOMATION_NAME"));
            capabilities.setCapability(MobileCapabilityType.APP, filePath.getAbsolutePath());
            driver = new AndroidDriver(new URL(PropertyReaderHelper.getProperties("APPIUM_URL")), capabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Start Automation device*/
    protected void startDevice() {
        try {
            File filePath = new File(fileSrc, PropertyReaderHelper.getProperties("BATCH_NAME"));
            Runtime.getRuntime().exec(filePath.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}
