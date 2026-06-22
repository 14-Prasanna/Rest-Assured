package tests;

import base.BaseTest;
import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.NotesPayload;
import services.NotesCreationService;
import utils.NoteManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class NoteCreationTest extends BaseTest {

    NotesCreationService notesCreationService =
            new NotesCreationService();

    @Test(priority = 20, dependsOnMethods = "tests.LoginTest.validLogin")
    public void createNoteValid() {

        Response response =
                notesCreationService.createNote(
                        NotesPayload.createNotePayload());

        response.then()
                .spec(ResponseSpec.responseSpec(201))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/CreateNodeSchema.json"))
                .body("success", equalTo(true))
                .body("message",
                        equalTo("Note created successfully"))
                .body("data._id", notNullValue())
                .log().all();

        String noteId =
                response.jsonPath().getString("data._id");

        NoteManager.setNoteId(noteId);

        System.out.println(
                "Created Note Id : "
                        + NoteManager.getNoteId());
    }

    @Test(priority = 21, dependsOnMethods = "tests.LoginTest.validLogin")
    public void createNoteWithoutToken() {

        Response response =
                notesCreationService.createNoteWithoutToken(
                        NotesPayload.createNotePayload());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/errorSchema.json"))
                .body("message[0].key",
                        equalTo("error"))
                .body("message[0].value",
                        equalTo("User is not logged in"))
                .log().all();
    }

    @Test(priority = 22, dependsOnMethods = "tests.LoginTest.validLogin")
    public void createNoteInvalidToken() {

        Response response =
                notesCreationService.createNoteInvalidToken(
                        NotesPayload.createNotePayload());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/errorSchema.json"))
                .body("message[0].key",
                        equalTo("error"))
                .body("message[0].value",
                        equalTo("Invalid or expired token"))
                .log().all();
    }

    @Test(priority = 23, dependsOnMethods = "tests.LoginTest.validLogin")
    public void createNoteWithNoBody() {

        Response response =
                notesCreationService.createNote(
                        NotesPayload.nobody());

        response.then()
                .spec(ResponseSpec.responseSpec(201))
                .body("success", equalTo(true))
                .body("message",
                        equalTo("Note created successfully"))
                .body("data.title",
                        equalTo("Untitled Note"))
                .body("data.content",
                        equalTo(""))
                .body("data.tags.size()",
                        equalTo(0))
                .body("data.color",
                        equalTo("#ffffff"))
                .body("data._id",
                        notNullValue())
                .log().all();
    }



    @Test(priority = 23, dependsOnMethods = "tests.LoginTest.validLogin")
    public void createNoteWithOptionalFields() {

        Response response =
                notesCreationService.createNote(
                        NotesPayload.optionalFieldsPayload());

        response.then()
                .spec(ResponseSpec.responseSpec(201))
                .body("success", equalTo(true))
                .body("message",
                        equalTo("Note created successfully"))
                .body("data.title",
                        equalTo("API Test in Postman"))
                .body("data.content",
                        equalTo("Created by Prasanna"))
                .body("data.isPinned",
                        equalTo(false))
                .body("data.tags.size()",
                        equalTo(0))
                .body("data.color",
                        equalTo("#ffffff"))
                .body("data._id",
                        notNullValue())
                .log().all();
    }


}