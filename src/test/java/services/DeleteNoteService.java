package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteNoteService {

    public Response deleteNote(String id){

        return given()
                .spec(RequestSpec.privateAPI())
                .pathParam("id", id)
                .when()
                .delete(Endpoints.DELETE_NOTE);
    }

    public Response deleteNoteInvalidToken(String id){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization","Bearer InvalidToken123")
                .pathParam("id", id)
                .when()
                .delete(Endpoints.DELETE_NOTE);
    }

    public Response deleteNoteWithoutToken(String id){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .pathParam("id", id)
                .when()
                .delete(Endpoints.DELETE_NOTE);
    }
}