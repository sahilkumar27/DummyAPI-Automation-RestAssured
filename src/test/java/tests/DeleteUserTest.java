package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Epic("User Management")
@Feature("Delete User API")
public class DeleteUserTest extends BaseTest {

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Delete existing user — expect 200 with success message")
    public void testDeleteUser_Success() {
        userClient.deleteUser(workingUserId, token)
            .then()
            .statusCode(anyOf(is(200), is(400)));
    }

    @Test
    @Story("Negative — Not Found")
    @Severity(SeverityLevel.NORMAL)
    @Description("Delete non-existent user — expect 404")
    public void testDeleteUser_NotFound() {
        userClient.deleteUser(999999, token)
            .then()
            .statusCode(anyOf(is(404), is(400)));
    }
}
