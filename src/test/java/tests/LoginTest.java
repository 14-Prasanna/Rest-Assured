package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.LoginPayload;
import services.LoginService;
import utils.TokenManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseTest {

    LoginService loginService = new LoginService();


    @Test(priority = 5, dependsOnMethods = "tests.HealthTest.verifyHealthCheckApi")
    public void validLogin() {

        Response response = loginService.postLogin(LoginPayload.validLoginPayload());

        response.then()
                .spec(ResponseSpec.responseSpec(201))
                .body(matchesJsonSchemaInClasspath("schemas/loginSuccessSchema.json"))
                .body("message[0].key", equalTo("success"))
                .body("message[0].value", equalTo("Admin logged in successfully"))
                .body("token", notNullValue())
                .body("user.email", equalTo("sam@gmail.com"))
                .log().all();



        TokenManager.setToken(response.jsonPath().getString("token"));
        System.out.println("Stored Token: " + TokenManager.getToken());

    }

    @Test(priority = 2, dependsOnMethods = "tests.HealthTest.verifyHealthCheckApi")
    public void invalidEmailLogin() {

        Response response = loginService.postLogin(LoginPayload.unknownEmailPayload());

        response.then()
                .spec(ResponseSpec.responseSpec(400))
                .body(matchesJsonSchemaInClasspath("schemas/loginErrorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("Email is invalid"))
                .log().all();
    }

    @Test(priority = 3, dependsOnMethods = "tests.HealthTest.verifyHealthCheckApi")
    public void invalidPasswordLogin() {

        Response response = loginService.postLogin(LoginPayload.wrongPasswordPayload());

        response.then()
                .spec(ResponseSpec.responseSpec(400))
                .body(matchesJsonSchemaInClasspath("schemas/loginErrorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("Password is incorrect"))
                .log().all();
    }

    @Test(priority = 4, dependsOnMethods = "tests.HealthTest.verifyHealthCheckApi")
    public void missingFieldsLogin() {

        Response response = loginService.postLogin(LoginPayload.missingFieldsPayload());

        response.then()
                .spec(ResponseSpec.responseSpec(400))
                .body(matchesJsonSchemaInClasspath("schemas/loginErrorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("All fields are required"))
                .log().all();
    }
}