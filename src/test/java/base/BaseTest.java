package base;

import clients.AuthClient;
import clients.UserClient;
import data.UserDataFactory;
import models.LoginRequest;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseTest {

    protected static String token;
    protected static int workingUserId = Integer.parseInt(ConfigReader.get("users.defaultId"));

    protected UserClient userClient;
    protected AuthClient authClient;

    @BeforeClass(alwaysRun = true)
    public void classSetup() {
        if (authClient == null) {
            authClient = new AuthClient();
        }
        if (userClient == null) {
            userClient = new UserClient();
        }
        token = fetchAccessToken();
    }

    protected String fetchAccessToken() {
        if (token != null) {
            return token;
        }

        LoginRequest loginRequest = UserDataFactory.defaultLogin();
        var loginResponse = authClient.login(loginRequest)
            .then()
            .statusCode(200)
            .extract();

        token = loginResponse.path("accessToken");
        Integer loginUserId = loginResponse.path("id");

        if (loginUserId != null) {
            workingUserId = loginUserId;
        }

        assertThat(token)
            .as("Access token should be present before running secured tests")
            .isNotBlank();

        return token;
    }
}
