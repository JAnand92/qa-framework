package utils.helpers;

import com.sun.istack.Nullable;
import io.appium.java_client.AppiumDriver;

import java.util.ArrayList;

public class AppiumHelper {

    /*Choose first Web view*/
    @Nullable
    private String getWebContext(AppiumDriver driver) {
        try {
            ArrayList<String> contexts = new ArrayList<>(driver.getContextHandles());
            for(String context : contexts) {
                if(!context.equals("NATIVE_APP")) {
                    return context;
                }
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
       return null;
    }
}
