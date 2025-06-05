package kuit.baemin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginRequest {

    @JsonCreator
    public LoginRequest(@JsonProperty("nickname") String nickname,
                        @JsonProperty("password") String password) {
        this.nickname = nickname;
        this.password = password;
    }

    private String nickname;
    private String password;
}
