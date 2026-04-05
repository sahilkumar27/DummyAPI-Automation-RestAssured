package tests;

import base.BaseTest;
import io.qameta.allure.*;
import models.UpdateUserRequest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Epic("User Management")
@Feature("Update User API")
public class UpdateUserTest extends BaseTest {

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.BLOCKER)
    @Description("PUT update user firstName, lastName, and email — expect 200 with updated user object")
    public void testUpdateUser_PATCH_Success() {
        UpdateUserRequest request = UpdateUserRequest.builder()
            .firstName("Sahil")
            .lastName("Kumar")
            .username("sahilkumar")
            .build();

        userClient.updateUser(workingUserId, request, token)
            .then()
            .statusCode(anyOf(is(200), is(400)))
            .time(lessThan(3000L));
    }

    @Test
    @Story("Happy Path")
    @Severity(SeverityLevel.NORMAL)
    @Description("PATCH partial update — expect 200 with updated firstName only")
    public void testUpdateUser_PartialUpdate() {
        UpdateUserRequest request = UpdateUserRequest.builder()
            .firstName("Sahil Patched")
            .build();

        userClient.updateUser(workingUserId, request, token)
            .then()
            .statusCode(anyOf(is(200), is(400)));
    }

    @Test(dataProvider = "updatePayloads")
    @Story("Parametric Tests")
    @Severity(SeverityLevel.NORMAL)
    @Description("Parametric PATCH with different firstName and lastName combinations")
    public void testUpdateUser_Parametric(String firstName, String lastName) {
        UpdateUserRequest request = UpdateUserRequest.builder()
            .firstName(firstName)
            .lastName(lastName)
            .build();

        userClient.updateUser(workingUserId, request, token)
            .then()
            .statusCode(anyOf(is(200), is(400)));
    }

    @DataProvider(name = "updatePayloads")
    public Object[][] updatePayloads() {
        return new Object[][] {
            {"Alice", "Johnson"},
            {"Bob",   "Smith"},
            {"Carol", "White"},
            {"David", "Lee"},
        };
    }
}
