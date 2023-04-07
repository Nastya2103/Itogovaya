package API;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties myProp = new Properties();
    private  static InputStream  config;
    private  static String baseUrl;
    private  static String pathLogin;
    private  static String pathGetPosts;

    @BeforeAll
    static void unitTest() throws IOException {
        config = new FileInputStream("./src/main/resources/my.properties");
        myProp.load(config);
        baseUrl=myProp.getProperty("baseUrl");
        pathLogin=myProp.getProperty("pathLogin");
        pathGetPosts=myProp.getProperty("pathGetPosts");
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getPathLogin() {
        return pathLogin;
    }

    public static String getPathGetPosts() {
        return pathGetPosts;
    }
}
