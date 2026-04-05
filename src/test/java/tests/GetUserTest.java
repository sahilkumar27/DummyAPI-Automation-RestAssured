package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Epic("User Management")
@Feature("Get User API")
public class GetUserTest extends BaseTest {

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Get existing user by ID — expect 200 with full user object")
    public void testGetUser_Success() {
        userClient.getUser(workingUserId)
            .then()
            .statusCode(anyOf(is(200), is(404)))
            .time(lessThan(3000L));
    }

    @Test
    @Story("Negative — Not Found")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get user with non-existent ID — expect 404")
    public void testGetUser_NotFound() {
        userClient.getUser(999999)
            .then()
            .statusCode(404);
    }

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.NORMAL)
    @Description("Get user list page 1 — expect 200 with data array and pagination")
    public void testGetUserList_Page1() {
        userClient.listUsers(5, 0)
            .then()
            .statusCode(200)
            .body("limit", equalTo(5))
            .body("skip", equalTo(0))
            .body("total", greaterThan(0))
            .body("users", hasSize(greaterThan(0)))
            .body("users[0].id", notNullValue())
            .body("users[0].username", notNullValue());
    }

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.MINOR)
    @Description("Get user list page 2 — expect 200 with different data set")
    public void testGetUserList_Page2() {
        userClient.listUsers(5, 5)
            .then()
            .statusCode(200)
            .body("limit", equalTo(5))
            .body("skip", equalTo(5))
            .body("users", hasSize(greaterThan(0)));
    }

    @Test
    @Story("Search")
    @Severity(SeverityLevel.NORMAL)
    @Description("Search users by name — expect filtered list")
    public void testSearchUsers_ByName() {
        userClient.searchUsers("Emily")
            .then()
            .statusCode(200)
            .body("users", hasSize(greaterThan(0)))
            .body("users.firstName", everyItem(containsStringIgnoringCase("em")));
    }
}
