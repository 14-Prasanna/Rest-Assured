package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetNoteByIdService {

    public Response getNoteById(String id){

        return given()
                .spec(RequestSpec.privateAPI())
                .pathParam("id", id)
                .when()
                .get(Endpoints.GET_NOTE_BY_ID);
    }

    public Response getNoteByIdInvalidToken(String id){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization","Bearer InvalidToken")
                .pathParam("id", id)
                .when()
                .get(Endpoints.GET_NOTE_BY_ID);
    }

    public Response getNoteByIdWithoutToken(String id){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .pathParam("id", id)
                .when()
                .get(Endpoints.GET_NOTE_BY_ID);
    }
}