package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetAllNotesService {

    public Response getAllNotes() {
        return given()
                .spec(RequestSpec.privateAPI())
                .when()
                .get(Endpoints.GET_ALL_NOTES);
    }

    public Response getAllNotesWithParams(Map<String, Object> params) {
        return given()
                .spec(RequestSpec.privateAPI())
                .queryParams(params)
                .when()
                .get(Endpoints.GET_ALL_NOTES);
    }

    public Response getAllNotesWithoutToken() {
        return given()
                .spec(RequestSpec.basicRequestSpec())
                .when()
                .get(Endpoints.GET_ALL_NOTES);
    }

    public Response getAllNotesWithInvalidToken() {
        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization", "Bearer invalid_token_123")
                .when()
                .get(Endpoints.GET_ALL_NOTES);
    }

    public Response getNoteByInvalidId(String id) {
        return given()
                .spec(RequestSpec.privateAPI())
                .pathParam("id", id)
                .when()
                .get(Endpoints.GET_NOTE_BY_ID);
    }
}