package tests;

import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.DeleteNoteService;
import utils.NoteManager;

import static org.hamcrest.Matchers.equalTo;

public class DeleteNoteTest {

    DeleteNoteService deleteNoteService = new DeleteNoteService();

    @Test(priority = 80, dependsOnMethods = "tests.LoginTest.validLogin")
    public void deleteNoteValid(){

        Response response =
                deleteNoteService.deleteNote(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .body("deletedCount",
                        equalTo(1))
                .body("deletedIds[0]",
                        equalTo(
                                NoteManager.getNoteId()))
                .log().all();
    }

    @Test(priority = 81, dependsOnMethods = "tests.LoginTest.validLogin")
    public void deleteNoteInvalidId(){

        Response response =
                deleteNoteService.deleteNote(
                        "123");

        response.then()
                .statusCode(400)
                .body("message",
                        equalTo(
                                "Invalid note ID format"))
                .log().all();
    }

    @Test(priority = 82, dependsOnMethods = "tests.LoginTest.validLogin")
    public void deleteNoteInvalidToken(){

        Response response =
                deleteNoteService.deleteNoteInvalidToken(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value",
                        equalTo("Invalid or expired token"))
                .log().all();
    }

    @Test(priority = 83, dependsOnMethods = "tests.LoginTest.validLogin")
    public void deleteNoteWithoutToken(){

        Response response =
                deleteNoteService.deleteNoteWithoutToken(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].key", equalTo("error"))
                .body("message[0].value",
                        equalTo("User is not logged in"))
                .log().all();
    }
}
