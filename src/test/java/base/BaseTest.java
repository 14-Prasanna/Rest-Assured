package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.util.Properties;

public class BaseTest {

    public static String baseUrl;

    @BeforeSuite
    public void setup() {

        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);

            baseUrl = properties.getProperty("baseUrl");
            RestAssured.baseURI = baseUrl;

            System.out.println("Base URL is: " + baseUrl);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties file");
        }
    }
}