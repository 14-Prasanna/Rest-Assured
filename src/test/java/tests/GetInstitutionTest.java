package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.GetInstitutionService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class GetInstitutionTest extends BaseTest {

    GetInstitutionService getInstitutionService = new GetInstitutionService();

    @Test(priority = 6, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getAllInstitution() {

        Response response = getInstitutionService.getAllInstitution();

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/getAllInstitutionSchema.json"))
                .body("message[0].key", equalTo("success"))
                .body("message[0].value", equalTo("Institution Retrieved successfully"))
                .body("getAllInstitution", notNullValue())
                .body("getAllInstitution.size()", greaterThan(1))
                .log().all();
    }
}