package tests;

import base.ResponseSpec;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.GetNoteByIdService;
import utils.NoteManager;

import static org.hamcrest.Matchers.*;

public class GetNoteByIdTest {

    GetNoteByIdService getNoteByIdService = new GetNoteByIdService();


    @Test(priority = 50, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getNoteByIdValid(){

        Response response =
                getNoteByIdService.getNoteById(
                        NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(200))
                .body("success", equalTo(true))
                .body("data._id",
                        equalTo(NoteManager.getNoteId()))
                .body("data.title",
                        notNullValue())
                .log().all();
    }

    @Test(priority = 51, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getNoteByIdInvalidId(){

        Response response =
                getNoteByIdService.getNoteById(
                        "12345");

        response.then()
                .statusCode(anyOf(
                        equalTo(400),
                        equalTo(404)
                ))
                .log().all();
    }

    @Test(priority = 52, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getNoteByIdInvalidToken(){

        Response response =
                getNoteByIdService
                        .getNoteByIdInvalidToken(
                                NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].value",
                        equalTo(
                                "Invalid or expired token"))
                .log().all();
    }

    @Test(priority = 53, dependsOnMethods = "tests.LoginTest.validLogin")
    public void getNoteByIdWithoutToken(){

        Response response =
                getNoteByIdService
                        .getNoteByIdWithoutToken(
                                NoteManager.getNoteId());

        response.then()
                .spec(ResponseSpec.responseSpec(401))
                .body("message[0].value",
                        equalTo(
                                "User is not logged in"))
                .log().all();
    }
}
