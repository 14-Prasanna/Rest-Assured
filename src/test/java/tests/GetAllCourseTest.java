package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.GetAllCourseService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class GetAllCourseTest extends BaseTest {

    GetAllCourseService getAllCourseService = new GetAllCourseService();

    @Test(priority = 10, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getAllCourseValid() {

        Response response = getAllCourseService.getAllCourse();

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllCourseSuccessSchema.json"))
                .body("message[0].key", equalTo("success"))
                .body("data", notNullValue())
                .body("data.size()", greaterThan(0))
                .body("data[0].courseName", notNullValue())
                .body("data[0].courseCode", notNullValue())
                .log().all();
    }

    @Test(priority = 11, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getAllCourseWithoutToken() {

        Response response = getAllCourseService.getAllCourseWithoutToken();

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath("schemas/errorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("User is not logged in"))
                .log().all();
    }

    @Test(priority = 12, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getAllCourseInvalidToken() {

        Response response = getAllCourseService.getAllCourseInvalidToken();

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath("schemas/errorSchema.json"))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value", equalTo("Invalid or expired token"))
                .log().all();
    }
}