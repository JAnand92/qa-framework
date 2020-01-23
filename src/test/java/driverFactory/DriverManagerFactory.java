package driverFactory;

public class DriverManagerFactory {
    private static DriverManager driverManager = null;
    public static DriverManager getManager(DriverType type) {
        try {
            switch (type) {
                case CHROME:
                    driverManager = new ChromeDriverManager();
                    break;
                case IE:
                    driverManager = new IEDriverManager();
                    break;
                case ANDROID:
                    driverManager = new AndroidDriverManager();
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return driverManager;
    }
}
