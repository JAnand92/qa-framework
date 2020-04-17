package driverFactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.helpers.PropertyReaderHelper;

import java.io.File;

public class ChromeDriverManager extends DriverManager{
    private ChromeDriverService chService;

    /*File path where chrome driver executable kept.*/
    private File fileSrc = new File(PropertyReaderHelper.getProperties("RESOURCE_FILE_PATH"));
    private File filePath = new File(fileSrc, "chromedriver.exe");

    @Override
    protected void startService() {
        try {
        Runtime.getRuntime().exec(PropertyReaderHelper
                .getProperties("CHROME_DRIVER_EXE"));
        if(null == chService) {
                chService = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File(filePath.getAbsolutePath()))
                        .usingAnyFreePort()
                        .build();
                chService.start();
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Override
    protected void stopService() {
        if(null != chService && chService.isRunning()) {
            try {
                chService.stop();
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
        }
    }

    @Override
    protected void createDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        System.setProperty("webdriver.chrome.driver", filePath.getAbsolutePath());
        driver = new ChromeDriver(options);
    }
}
