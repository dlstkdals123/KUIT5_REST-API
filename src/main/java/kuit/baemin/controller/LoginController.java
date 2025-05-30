package kuit.baemin.controller;

import kuit.baemin.domain.User;
import kuit.baemin.dto.LoginRequest;
import kuit.baemin.exception.InvalidLoginException;
import kuit.baemin.service.UserServiceV4;
import kuit.baemin.utils.BaseResponse;
import kuit.baemin.utils.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class LoginController {

    private final UserServiceV4 usersService;

    @PostMapping("/login")
    public BaseResponse<User> login (@RequestBody LoginRequest loginRequest) {
        log.info("login request - nickname: {}, password : {}",
                loginRequest.getNickname(), loginRequest.getPassword());

        User user = usersService.find(loginRequest);
        if (!user.getPassword().equals(loginRequest.getPassword()))
            throw new InvalidLoginException(BaseResponseStatus.NON_FOUND_USER.getResponseMessage());

        return new BaseResponse<>(user);
    }

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleEmptyResultDataAccessException(InvalidLoginException e) {
        return new BaseResponse<>(BaseResponseStatus.NON_FOUND_USER);
    }
}
