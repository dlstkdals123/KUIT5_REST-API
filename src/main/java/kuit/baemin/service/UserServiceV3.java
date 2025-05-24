package kuit.baemin.service;

import kuit.baemin.domain.User;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.repository.UserRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class UserServiceV3 {

    private final PlatformTransactionManager  transactionManager;
    private final UserRepositoryV3 userRepository;

    public void save(SignupRequest signupRequest) throws SQLException {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {

            userRepository.save(new User(signupRequest.getEmail(), signupRequest.getPassword()));
            validateEmail();

            transactionManager.commit(status); //성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status); //실패시 롤백
            throw new IllegalStateException(e);
        }

    }

    private static void validateEmail() {
        log.error("이메일 검증 오류");
        throw new RuntimeException();
    }
}
