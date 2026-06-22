package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.GetRolesService;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.hamcrest.Matchers.*;

public class GetRolesTest extends BaseTest {

    GetRolesService getRolesService = new GetRolesService();

    @Test(priority = 9, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getRolesWithValidToken() {

        Response response = getRolesService.getRolesWithValidToken();

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getRolesSuccessSchema.json"))
                .body("message[0].key", equalTo("success"))
                .body("message[0].value", equalTo("Role Retrieved successfully"))
                .body("roles", notNullValue())
                .body("roles.size()", greaterThan(0))
                .log().all();
    }

    @Test(priority = 7, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getRolesWithInvalidToken() {

        Response response = getRolesService.getRolesWithInvalidToken();

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath("schemas/errorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("Invalid or expired token"))
                .log().all();
    }

    @Test(priority = 8, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getRolesWithNoToken(){

        Response response = getRolesService.getRolesWithoutToken();

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath("schemas/errorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("User is not logged in"))
                .log().all();
    }
}