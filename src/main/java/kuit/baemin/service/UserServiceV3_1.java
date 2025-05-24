package kuit.baemin.service;

import kuit.baemin.domain.User;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.repository.UserRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

/**
 * 스프링 트랜잭션
 */
@Slf4j
@RequiredArgsConstructor
public class UserServiceV3_1 {

    private final UserRepositoryV3 userRepository;

    @Transactional
    public void save(SignupRequest signupRequest) throws SQLException {
        userRepository.save(new User(signupRequest.getEmail(), signupRequest.getPassword()));
        validateEmail();
    }

    private static void validateEmail() {
        log.error("이메일 검증 오류");
        throw new RuntimeException();
    }
}
