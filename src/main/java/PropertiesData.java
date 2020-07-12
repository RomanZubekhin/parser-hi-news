import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesData {
    static {
        loadProperties();
    }
    static Properties prop;
    private static void loadProperties() {
        prop = new Properties();
        InputStream in = PropertiesData.class
                .getResourceAsStream("config.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
