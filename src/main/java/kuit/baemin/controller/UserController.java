package kuit.baemin.controller;

import kuit.baemin.domain.User;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.service.UserServiceV4;
import kuit.baemin.utils.BaseResponse;
import kuit.baemin.utils.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequiredArgsConstructor
@Slf4j
@RestController
public class UserController {

    private final UserServiceV4 usersService;

    // 객체 to json
    @PostMapping("/users")
//    @ResponseBody
    public BaseResponse<User> signup (@Validated @RequestBody SignupRequest signupRequest) {
        log.info("signup request - nickname: {}, password : {}, email : {}, phone_number : {}",
                signupRequest.getNickname(), signupRequest.getPassword(), signupRequest.getEmail(), signupRequest.getPhoneNumber());

        User user = usersService.save(signupRequest);
        return new BaseResponse<>(user);
    }

    //  오류 응답
//    @PostMapping("/users")
//    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> signup6 (SignupRequest signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getEmail(), signupRequest.getPassword());

        return new BaseResponse<>(BaseResponseStatus.DUPLICATED_EMAIL);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleSQLException(SQLException ex) {
        if (ex.getMessage().contains("email"))
            return new BaseResponse<>(BaseResponseStatus.DUPLICATED_EMAIL);

        return new BaseResponse<>(BaseResponseStatus.DUPLICATED_NICKNAME);
    }


}
