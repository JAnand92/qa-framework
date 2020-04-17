package driverFactory;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.helpers.PropertyReaderHelper;

import java.io.File;

public class IEDriverManager extends DriverManager{
    private InternetExplorerDriverService ieService;

    /*File path where IE driver executable kept.*/
    private File fileSrc = new File(PropertyReaderHelper.getProperties("RESOURCE_FILE_PATH"));
    private File filePath = new File(fileSrc, "IEDriverServer.exe");

    @Override
    protected void startService() {
        if(null == ieService) {
            try {
                ieService = new InternetExplorerDriverService.Builder()
                        .usingDriverExecutable(new File(filePath.getAbsolutePath()))
                        .usingAnyFreePort()
                        .build();
                ieService.start();
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }
    }

    @Override
    protected void stopService() {
        if(null != ieService && ieService.isRunning()) {
            try {
                ieService.stop();
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }
    }

    @Override
    protected void createDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        InternetExplorerOptions options = new InternetExplorerOptions();
        capabilities.setCapability(String.valueOf(InternetExplorerOptions.class), options);
        System.setProperty("webdriver.ie.driver", filePath.getAbsolutePath());
        driver = new InternetExplorerDriver(options);
    }
}
