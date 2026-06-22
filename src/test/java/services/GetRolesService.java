package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetRolesService {

    public Response getRolesWithValidToken() {
        return given()
                .spec(RequestSpec.privateAPI())
                .when()
                .get(Endpoints.GET_ROLES_ALL);
    }

    public Response getRolesWithoutToken() {
        return given()
                .spec(RequestSpec.basicRequestSpec())
                .when()
                .get(Endpoints.GET_ROLES_ALL);
    }

    public Response getRolesWithInvalidToken() {
        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization", "Bearer invalid_token_123")
                .when()
                .get(Endpoints.GET_ROLES_ALL);
    }
}