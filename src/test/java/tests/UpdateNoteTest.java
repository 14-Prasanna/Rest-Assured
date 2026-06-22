package tests;

import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.UpdateNotePayload;
import services.UpdateNoteService;
import utils.NoteManager;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class UpdateNoteTest {

    UpdateNoteService updateNoteService = new UpdateNoteService();

    @Test(priority = 60, dependsOnMethods = "tests.LoginTest.validLogin")
    public void updateNoteValid(){

        Response response =
                updateNoteService.updateNote(
                        NoteManager.getNoteId(),
                        UpdateNotePayload.updatePayload());

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .body("message",
                        equalTo(
                                "Note updated successfully"))
                .body("data.title",
                        equalTo(
                                "API Test Note (edited)"))
                .body("data.content",
                        equalTo(
                                "Updated content"))
                .log().all();
    }


    @Test(priority = 61, dependsOnMethods = "tests.LoginTest.validLogin")
    public void updateNoteInvalidId(){

        Response response =
                updateNoteService.updateNote(
                        "123",
                        UpdateNotePayload.updatePayload());

        response.then()
                .statusCode(anyOf(
                        equalTo(400),
                        equalTo(404)))
                .log().all();
    }

    @Test(priority = 62, dependsOnMethods = "tests.LoginTest.validLogin")
    public void updateNoteInvalidToken(){

        Response response =
                updateNoteService.updateNoteInvalidToken(
                        NoteManager.getNoteId(),
                        UpdateNotePayload.updatePayload());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].value",
                        equalTo(
                                "Invalid or expired token"))
                .log().all();
    }


    @Test(priority = 63, dependsOnMethods = "tests.LoginTest.validLogin")
    public void updateNoteWithoutToken(){

        Response response =
                updateNoteService.updateNoteWithoutToken(
                        NoteManager.getNoteId(),
                        UpdateNotePayload.updatePayload());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].value",
                        equalTo(
                                "User is not logged in"))
                .log().all();
    }

    @Test(priority = 64, dependsOnMethods = "tests.LoginTest.validLogin")
    public void updateOnlyTitle(){

        Map<String,Object> payload =
                new HashMap<>();

        payload.put(
                "title",
                "Only Title Updated");

        Response response =
                updateNoteService.updateNote(
                        NoteManager.getNoteId(),
                        payload);

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("data.title",
                        equalTo(
                                "Only Title Updated"))
                .log().all();
    }


}

