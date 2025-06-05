package kuit.baemin.service;

import kuit.baemin.domain.User;
import kuit.baemin.dto.LoginRequest;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.dto.UserUpdateRequest;
import kuit.baemin.exception.InvalidLoginException;
import kuit.baemin.repository.UserRepositoryV6;
import kuit.baemin.utils.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private final UserRepositoryV6 userRepository;

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
    public User findByLoginRequest(LoginRequest loginRequest) {
        try {
            User user = userRepository.findByNickname(loginRequest.getNickname());

            if (!user.getPassword().equals(loginRequest.getPassword()))
                throw new InvalidLoginException(BaseResponseStatus.NON_MATCH_CREDENTIALS.getResponseMessage());

            return user;

        } catch (EmptyResultDataAccessException e) {
            throw new InvalidLoginException(BaseResponseStatus.NON_MATCH_CREDENTIALS.getResponseMessage());
        }
    }

    @Transactional
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    private static void validateEmail() {
        log.error("이메일 검증 오류");
        throw new RuntimeException();
    }

    @Transactional
    public User update(Long userId, UserUpdateRequest userUpdateRequest) {
        return userRepository.update(userId, User.builder()
                .userId(userId)
                .nickname(userUpdateRequest.getNickname())
                .password(userUpdateRequest.getPassword())
                .email(userUpdateRequest.getEmail())
                .phoneNumber(userUpdateRequest.getPhoneNumber())
                .build());
    }
}
