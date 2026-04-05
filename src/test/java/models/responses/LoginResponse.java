package models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
}
