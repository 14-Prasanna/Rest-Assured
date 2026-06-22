package tests;

import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.TogglePinService;
import utils.NoteManager;

import static org.hamcrest.Matchers.*;

public class TogglePinTest {

    TogglePinService togglePinService = new TogglePinService();

    @Test(priority = 70, dependsOnMethods = "tests.LoginTest.validLogin")
    public void togglePinValid(){

        Response response =
                togglePinService.togglePin(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .body("data.isPinned", equalTo(true))
                .log().all();
    }

    @Test(priority = 71, dependsOnMethods = "tests.LoginTest.validLogin")
    public void togglePinInvalidId(){

        Response response =
                togglePinService.togglePin("123");

        response.then()
                .statusCode(anyOf(equalTo(400), equalTo(404)))
                .log().all();
    }

    @Test(priority = 72, dependsOnMethods = "tests.LoginTest.validLogin")
    public void togglePinInvalidToken(){

        Response response =
                togglePinService.togglePinInvalidToken(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value",
                        equalTo("Invalid or expired token"))
                .log().all();
    }

    @Test(priority = 73, dependsOnMethods = "tests.LoginTest.validLogin")
    public void togglePinWithoutToken(){

        Response response =
                togglePinService.togglePinWithoutToken(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value",
                        equalTo("User is not logged in"))
                .log().all();
    }
}