package clients;

import io.restassured.response.Response;
import models.RegisterRequest;
import models.UpdateUserRequest;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final String USERS = "/users";
    private static final String USERS_ADD = USERS + "/add";
    private static final String USERS_SEARCH = USERS + "/search";

    public Response createUser(RegisterRequest request) {
        return given()
            .spec(spec)
            .body(request)
        .when()
            .post(USERS_ADD);
    }

    public Response getUser(int userId) {
        return given()
            .spec(spec)
        .when()
            .get(USERS + "/" + userId);
    }

    public Response listUsers(int limit, int skip) {
        return given()
            .spec(spec)
            .queryParam("limit", limit)
            .queryParam("skip", skip)
        .when()
            .get(USERS);
    }

    public Response searchUsers(String query) {
        return given()
            .spec(spec)
            .queryParam("q", query)
        .when()
            .get(USERS_SEARCH);
    }

    public Response updateUser(int userId, UpdateUserRequest request, String token) {
        return given()
            .spec(withAuth(token))
            .body(request)
        .when()
            .put(USERS + "/" + userId);
    }

    public Response deleteUser(int userId, String token) {
        return given()
            .spec(withAuth(token))
        .when()
            .delete(USERS + "/" + userId);
    }
}
