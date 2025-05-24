package kuit.baemin.service;

import kuit.baemin.domain.User;
import kuit.baemin.dto.SignupRequest;
import kuit.baemin.repository.UserRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션
 */
@Slf4j
@RequiredArgsConstructor
public class UserServiceV2 {

    private final DataSource dataSource;
    private final UserRepositoryV2 userRepository;

    public void save(SignupRequest signupRequest) throws SQLException {

        Connection connection = dataSource.getConnection();

        try {
            connection.setAutoCommit(false);

            userRepository.save(connection, new User(signupRequest.getEmail(), signupRequest.getPassword()));
            validateEmail();

            connection.commit(); //성공시 커밋
        } catch (Exception e) {
            connection.rollback(); //실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(connection);
        }

    }

    private static void validateEmail() {
        log.error("이메일 검증 오류");
        throw new RuntimeException();
    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); //커넥션 풀 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }


}
