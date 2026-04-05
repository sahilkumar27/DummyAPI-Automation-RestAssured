package clients;

import io.restassured.response.Response;
import models.LoginRequest;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseClient {

    private static final String LOGIN_ENDPOINT = "/auth/login";

    public Response login(LoginRequest request) {
        return given()
            .spec(spec)
            .body(request)
        .when()
            .post(LOGIN_ENDPOINT);
    }
}
