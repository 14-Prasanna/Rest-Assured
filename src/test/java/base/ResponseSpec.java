package base;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpec {

    public static ResponseSpecification launch(int status){
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }

    public static ResponseSpecification responseSpec(int status) {

        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .expectContentType(ContentType.JSON)
                .build();
    }
}