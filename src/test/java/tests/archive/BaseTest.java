package tests.archive;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.ServerSocket;
import java.net.URL;


public class BaseTest {
    private static AppiumDriverLocalService service;
    public static AndroidDriver androidDriver;
    private static String nodeExe = "taskkill /F /IM node.exe";
    private static String fileLocation = "src\\test\\java\\resources";
    private static String apiDemoApk = "ApiDemos-debug.apk";
    private static String batFileName = "StartJATestEmulator.bat";
    private static String automationName = "uiautomator2";
    private static String deviceName = null;
    /*Set URL path for Appium server.
     * Store the appium server url. http//127.0.0.1 --> common local host for all window machine. Port: 4723*/
    private static String appiumServer = "http://127.0.0.1:4723/wd/hub";
    private static int portNumber = 4723;

    @BeforeTest
    public static void beforeTest() {
        try {
            killAppiumNode();
            startAppiumServer();
            startEmulator();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @AfterTest
    public static void afterTest() {
        try {
            stopAppiumServer();
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Kill Appium Node!*/
    private static void killAppiumNode() {
        try {
            System.out.println("*** Closing Appium Server Node...***");
            Process process = Runtime.getRuntime().exec(nodeExe);
            if(process.isAlive()) {
                System.out.println("\n*** Appium Server Node closed...***");
                process.destroy();
            } else {
                System.out.println("\n*** Appium Server Node is not closed...***");
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Check weather Appium Server is running or not!*/
    private static boolean checkAppiumServerRunning(int portNumber) {
        boolean isAppiumServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            serverSocket.close();
        } catch (Exception e) {
            /*In case, server is running - port is in use!*/
            isAppiumServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isAppiumServerRunning;
    }

    /*Start the Appium Server!*/
    private static void startAppiumServer() {
        try {
            System.out.println("\n*** Checking - Appium is not started already...***");
            boolean isServerRunning = checkAppiumServerRunning(portNumber);
            System.out.println("\n*** Starting Appium Server...***");
            service = AppiumDriverLocalService.buildDefaultService();
            if(!isServerRunning) {
                service.start();
                if(service == null || !service.isRunning()) {
                    throw new AppiumServerHasNotBeenStartedLocallyException("" +
                            "\nAn Appium Server node is not started!");
                }
                System.out.println("\n*** Appium Server Started...***");
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Invoke Mobile Application!*/
    public static void setupAppium(String apkName, String deviceName) {
        try{
            /*File path: where mobile application is kept.*/
            File filePath = new File(new File(fileLocation), apkName);

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            /*This below code will be called if we provide deviceName at run time while building project through
            * Maven...Add -D deviceName in the mvn command! */
           /* deviceName = System.getProperty("deviceName");
            if(deviceName.contains("emulator")) {
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            } else if(deviceName.contains("real")) {
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            }*/

            /*Open emulator*/
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

            /*Open physical device*/
            /* desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");*/

            /*Android updated its internal framework to uiautomator2 and through Appium code,
            we need to tell that we need to access uiautomator2 elements of Android.*/
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);

            /*Open mobile application*/
            desiredCapabilities.setCapability(MobileCapabilityType.APP, filePath.getAbsolutePath());

            /*Connect to the Appium Server*/
            androidDriver = new AndroidDriver(new URL(appiumServer), desiredCapabilities);
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Start Application Virtual Device!*/
    private static void startEmulator() {
        try{
            File filePath = new File( new File(fileLocation), batFileName);
            System.out.println("\n*** Starting Emulator...***");
            Runtime.getRuntime().exec(filePath.getAbsolutePath());
            Thread.sleep(10000);
            System.out.println("\n*** Emulator Started...***");
        } catch(Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /*Stop Appium Server!*/
    private static void stopAppiumServer() {
        try {
            System.out.println("\n*** Stopping Appium Server...***");
            service.stop();
            System.out.println("\n*** Appium Server Stopped...***");
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}



