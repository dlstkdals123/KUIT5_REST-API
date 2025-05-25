package kuit.baemin.repository;

import kuit.baemin.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * JdbcTemplate 사용
 */
@Slf4j
@Repository
public class UserRepositoryV6 implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryV6(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User save(User user)  {
        String sql = "insert into user(email, password) " +
                "values (?, ?)";

        jdbcTemplate.update(sql, user.getEmail(), user.getPassword());

        return user;
    }

}
