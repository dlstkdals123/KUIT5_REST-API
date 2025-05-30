package kuit.baemin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter
public class SignupRequest {

    @JsonCreator
    public SignupRequest(@JsonProperty("email") String email,
                         @JsonProperty("password") String password,
                         @JsonProperty("confirm_password") String confirmPassword,
                         @JsonProperty("nickname") String nickname,
                         @JsonProperty("phone_number") String phoneNumber) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    @Email
    private String email;
    @Size(min = 6, max = 20)
    private String password;
    private String confirmPassword;
    private String nickname;
    @Size(min = 11, max = 20)
    private String phoneNumber;
}
