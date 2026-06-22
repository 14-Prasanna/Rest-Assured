package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TogglePinService {

    public Response togglePin(String id){

        return given()
                .spec(RequestSpec.privateAPI())
                .pathParam("id", id)
                .when()
                .put(Endpoints.TOGGLE_PIN);
    }

    public Response togglePinInvalidToken(String id){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization","Bearer InvalidToken123")
                .pathParam("id", id)
                .when()
                .put(Endpoints.TOGGLE_PIN);
    }

    public Response togglePinWithoutToken(String id){

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .pathParam("id", id)
                .when()
                .put(Endpoints.TOGGLE_PIN);
    }
}