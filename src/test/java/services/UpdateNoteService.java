package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateNoteService {

    public Response updateNote(String id,
                               Map<String,Object> payload){

        return given()
                .spec(RequestSpec.privateAPI())
                .pathParam("id", id)
                .body(payload)
                .when()
                .put(Endpoints.UPDATE_NOTE);
    }

    public Response updateNoteInvalidToken(
            String id,
            Map<String,Object> payload){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization",
                        "Bearer InvalidToken123")
                .pathParam("id", id)
                .body(payload)
                .when()
                .put(Endpoints.UPDATE_NOTE);
    }

    public Response updateNoteWithoutToken(
            String id,
            Map<String,Object> payload){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .pathParam("id", id)
                .body(payload)
                .when()
                .put(Endpoints.UPDATE_NOTE);
    }
}