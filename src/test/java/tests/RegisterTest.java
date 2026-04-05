package tests;

import base.BaseTest;
import io.qameta.allure.*;
import data.UserDataFactory;
import models.RegisterRequest;
import models.responses.UserResponse;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Epic("User Management")
@Feature("User Registration")
public class RegisterTest extends BaseTest {

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Register a new user with username, email, firstName, and lastName")
    public void testRegister_Success() {
        RegisterRequest request = UserDataFactory.randomUser();

        UserResponse createdUser = userClient.createUser(request)
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("username", equalTo(request.getUsername()))
            .body("email", equalTo(request.getEmail()))
            .body("firstName", equalTo(request.getFirstName()))
            .body("lastName", equalTo(request.getLastName()))
            .body(matchesJsonSchemaInClasspath("schemas/register-schema.json"))
            .time(lessThan(3000L))
            .extract().as(UserResponse.class);

    }

    @Test
    @Story("Negative — Missing Fields")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register without username — API currently accepts and creates user, validate creation")
    public void testRegister_MissingUsername() {
        RegisterRequest request = RegisterRequest.builder()
            .email("john.doe@example.com")
            .firstName("John")
            .lastName("Doe")
            .build();

        userClient.createUser(request)
            .then()
            .statusCode(201)
            .body("id", notNullValue());
    }

    @Test
    @Story("Negative — Empty Body")
    @Severity(SeverityLevel.MINOR)
    @Description("Register with empty JSON body — API currently creates placeholder user")
    public void testRegister_EmptyBody() {
        userClient.createUser(RegisterRequest.builder().build())
            .then()
            .statusCode(201)
            .body("id", notNullValue());
    }
}
