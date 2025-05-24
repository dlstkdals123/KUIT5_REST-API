package kuit.baemin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter
public class SignupRequest {

    @JsonCreator
    public SignupRequest(@JsonProperty("email") String email,
                         @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;
}
