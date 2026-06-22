package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class NotesCreationService {

    public Response createNote(Map<String,Object> payload){

        return given()
                .spec(RequestSpec.privateAPI())
                .body(payload)
                .when()
                .post(Endpoints.CREATE_NOTES);
    }

    public Response createNoteWithoutToken(Map<String,Object> payload){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.CREATE_NOTES);
    }

    public Response createNoteInvalidToken(Map<String,Object> payload){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization","Bearer InvalidToken123")
                .body(payload)
                .when()
                .post(Endpoints.CREATE_NOTES);
    }
}