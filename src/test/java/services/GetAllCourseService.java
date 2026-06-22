package services;

import base.RequestSpec;
import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetAllCourseService {

    public Response getAllCourse() {

        return given()
                .spec(RequestSpec.privateAPI())
                .when()
                .get(Endpoints.GET_ALL_COURSE);
    }

    public Response getAllCourseWithoutToken() {

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .when()
                .get(Endpoints.GET_ALL_COURSE);
    }

    public Response getAllCourseInvalidToken() {

        return given()
                .spec(RequestSpec.basicRequestSpec())
                .header("Authorization", "Bearer InvalidToken123")
                .when()
                .get(Endpoints.GET_ALL_COURSE);
    }
}