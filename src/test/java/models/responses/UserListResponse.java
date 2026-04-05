package models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResponse {
    private List<UserResponse> users;
    private Integer total;
    private Integer skip;
    private Integer limit;
}
