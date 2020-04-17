package utils.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReaderHelper {
    public static String getProperties(String property) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(new File("src")
                    .getAbsolutePath() + "\\global.properties");
            prop.load(input);
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        } finally {
            if(null != input) {
                try {
                    input.close();
                } catch (Exception e) {
                    System.out.println(e.fillInStackTrace());
                }
            }
        }
        return prop.getProperty(property);
    }
}
