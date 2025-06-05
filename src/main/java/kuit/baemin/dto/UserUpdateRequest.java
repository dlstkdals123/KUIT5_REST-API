package kuit.baemin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @JsonCreator
    public UserUpdateRequest(@JsonProperty("email") String email,
                         @JsonProperty("password") String password,
                         @JsonProperty("nickname") String nickname,
                         @JsonProperty("phone_number") String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    @Email
    private String email;
    @Size(min = 6, max = 20)
    private String password;
    private String nickname;
    @Size(min = 11, max = 20)
    private String phoneNumber;
}
