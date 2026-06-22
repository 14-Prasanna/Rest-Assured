package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginService {

    public Response postLogin(Map<String, Object> payload) {

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .body(payload)
                .when()
                .post(Endpoints.LOGIN);
    }
}