package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
