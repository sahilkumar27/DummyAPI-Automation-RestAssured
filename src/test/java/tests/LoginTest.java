package tests;

import base.BaseTest;
import io.qameta.allure.*;
import data.UserDataFactory;
import models.LoginRequest;
import models.responses.LoginResponse;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Epic("User Management")
@Feature("User Login")
public class LoginTest extends BaseTest {

    @Test(priority = 1)
    @Story("Happy Path")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with valid username and password — expect 200 + accessToken")
    public void testLogin_Success() {
        LoginRequest request = UserDataFactory.defaultLogin();

        LoginResponse response = authClient.login(request)
            .then()
            .statusCode(200)
            .body("accessToken", notNullValue())
            .body("refreshToken", notNullValue())
            .time(lessThan(3000L))
            .extract().as(LoginResponse.class);

        token = response.getAccessToken();
    }

    @Test
    @Story("Negative — Wrong Credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with wrong password should fail with 400")
    public void testLogin_WrongPassword() {
        LoginRequest request = LoginRequest.builder()
            .username("emilys")
            .password("wrongpassword")
            .build();

        authClient.login(request)
            .then()
            .statusCode(400);
    }

    @Test
    @Story("Negative — Empty Body")
    @Severity(SeverityLevel.MINOR)
    @Description("Login with empty body should fail")
    public void testLogin_EmptyBody() {
        authClient.login(LoginRequest.builder().build())
            .then()
            .statusCode(400);
    }
}
