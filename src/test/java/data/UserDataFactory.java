package data;

import models.LoginRequest;
import models.RegisterRequest;
import models.UpdateUserRequest;
import utils.ConfigReader;

import java.util.UUID;

public final class UserDataFactory {

    private UserDataFactory() {
    }

    public static LoginRequest defaultLogin() {
        return LoginRequest.builder()
            .username(ConfigReader.get("auth.username"))
            .password(ConfigReader.get("auth.password"))
            .build();
    }

    public static RegisterRequest randomUser() {
        String suffix = UUID.randomUUID().toString().substring(0, 6);
        return RegisterRequest.builder()
            .username("user" + suffix)
            .email("user" + suffix + "@example.com")
            .firstName("Test")
            .lastName("User" + suffix)
            .password("P@ssw0rd!" + suffix)
            .build();
    }

    public static UpdateUserRequest updatedName(String first, String last) {
        return UpdateUserRequest.builder()
            .firstName(first)
            .lastName(last)
            .build();
    }
}
