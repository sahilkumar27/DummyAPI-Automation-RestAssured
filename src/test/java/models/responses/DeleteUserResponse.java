package models.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteUserResponse {
    @JsonProperty("isDeleted")
    private boolean deleted;
    private String deletedOn;
}
