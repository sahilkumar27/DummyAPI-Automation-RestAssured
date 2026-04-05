package models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
}
