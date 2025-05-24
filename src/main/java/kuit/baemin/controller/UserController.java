package kuit.baemin.controller;

import kuit.baemin.domain.User;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.utils.BaseResponse;
import kuit.baemin.utils.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController {

    //  기본
    @PostMapping("/users")
    @ResponseBody
    public String signup1 (@RequestBody SignupRequest signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getEmail(), signupRequest.getPassword());

        return "ok";
    }

    // 요청 파라미터로 HttpEntity로 매핑
//    @PostMapping("/users")
    @ResponseBody
    public String signup2 (HttpEntity<SignupRequest> signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getBody().getEmail(), signupRequest.getBody().getPassword());

        return "ok";
    }

    // 요청 파라미터와 응답 타입으로 HttpEntity 사용
//    @PostMapping("/users")
    public HttpEntity<String> signup3 (HttpEntity<SignupRequest> signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getBody().getEmail(), signupRequest.getBody().getPassword());

        return new HttpEntity<>("ok");
    }

    //  @RequestBody 제거
//    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String signup4 (SignupRequest signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getEmail(), signupRequest.getPassword());

        return "no";
    }

    // 객체 to json
//    @PostMapping("/users")
    @ResponseBody
    public BaseResponse<User> signup5 (@RequestBody SignupRequest signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getEmail(), signupRequest.getPassword());

        User user = new User(signupRequest.getEmail(), signupRequest.getPassword());

        return new BaseResponse<>(user);
    }

    //  오류 응답
//    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> signup6 (SignupRequest signupRequest) {
        log.info("signup request - email : {}, password : {}",
                signupRequest.getEmail(), signupRequest.getPassword());

        return new BaseResponse<>(BaseResponseStatus.DUPLICATED_EMAIL);
    }


}
