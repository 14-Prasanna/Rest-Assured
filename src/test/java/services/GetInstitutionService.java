package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetInstitutionService {

    public Response getAllInstitution() {

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .when()
                .get(Endpoints.GET_ALL_INSTITUTION);
    }
}