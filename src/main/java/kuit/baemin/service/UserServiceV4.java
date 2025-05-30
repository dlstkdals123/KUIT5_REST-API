package kuit.baemin.service;

import kuit.baemin.domain.User;
import kuit.baemin.dto.LoginRequest;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 * MemberRepository 인터페이스 의존
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceV4 {

    private final UserRepository userRepository;

    @Transactional
    public User save(SignupRequest signupRequest) {
        return userRepository.save(User.builder()
                .nickname(signupRequest.getNickname())
                .password(signupRequest.getPassword())
                .email(signupRequest.getEmail())
                .phoneNumber(signupRequest.getPhoneNumber())
                .build());
    }

    @Transactional
    public User find(LoginRequest loginRequest) {
        return userRepository.find(User.builder()
                .nickname(loginRequest.getNickname())
                .password(loginRequest.getPassword())
                .build());
    }

    private static void validateEmail() {
        log.error("이메일 검증 오류");
        throw new RuntimeException();
    }
}
