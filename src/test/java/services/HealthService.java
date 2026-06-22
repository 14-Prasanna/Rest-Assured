package services;

import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HealthService {

    public Response getHealthCheck() {

        return given()
                .when()
                .get(Endpoints.HEALTH);
    }

    public Response getInvalidEndpoint(String invalid) {

        return given()
                .when()
                .get(invalid);
    }
}