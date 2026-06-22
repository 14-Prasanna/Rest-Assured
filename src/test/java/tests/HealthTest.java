package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.HealthService;

import static org.hamcrest.Matchers.*;

public class HealthTest extends BaseTest {

    HealthService healthService = new HealthService();

    @Test(priority = 1)
    public void verifyHealthCheckApi() {

        Response response = healthService.getHealthCheck();

        response.then()
                .spec(ResponseSpec.launch(200))
                .body("message", notNullValue())
                .log().all();

        System.out.println(response.asPrettyString());
    }

    @Test(priority = 2)
    public void verifyInvalidHealthEndpoint() {

        Response response = healthService.getInvalidEndpoint("/invalid-endpoint");

        response.then()
                .spec(ResponseSpec.launch(404))
                .log().all();
    }

}