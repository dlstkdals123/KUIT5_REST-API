package kuit.baemin.controller;

import jakarta.servlet.http.HttpSession;
import kuit.baemin.domain.User;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.dto.UserUpdateRequest;
import kuit.baemin.exception.UnauthorizedException;
import kuit.baemin.service.UserServiceV4;
import kuit.baemin.utils.BaseResponse;
import kuit.baemin.utils.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/users/{userId}")
    public BaseResponse<User> getUser (@PathVariable Long userId, HttpSession session) {
        log.info("get user - userId: {}", userId);

        ValidateUser(userId, session);

        User user = usersService.findByUserId(userId);
        return new BaseResponse<>(user);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return new BaseResponse<>(BaseResponseStatus.USER_NOT_FOUND);
    }

    @PutMapping("/users/{userId}")
    public BaseResponse<User> updateUser (@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest, HttpSession session) {
        log.info("update user - userId: {}, nickname: {}, password: {}, email: {}, phoneNumber: {}",
                userId, userUpdateRequest.getNickname(), userUpdateRequest.getPassword(), userUpdateRequest.getEmail(), userUpdateRequest.getPhoneNumber());

        ValidateUser(userId, session);

        User updateUser = usersService.update(userId, userUpdateRequest);
        return new BaseResponse<>(updateUser);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse<User> handleUnauthorizedException(UnauthorizedException ex) {
        return new BaseResponse<>(BaseResponseStatus.UNAUTHORIZED);
    }

    private void ValidateUser(Long userId, HttpSession session) {
        if (session.getAttribute("user") == null)
            throw new UnauthorizedException(BaseResponseStatus.UNAUTHORIZED.getResponseMessage());

        User loginUser = (User) session.getAttribute("user");
        if (!loginUser.getUserId().equals(userId))
            throw new UnauthorizedException(BaseResponseStatus.UNAUTHORIZED.getResponseMessage());
    }
}
