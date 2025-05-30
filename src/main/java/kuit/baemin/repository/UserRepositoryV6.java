package kuit.baemin.repository;

import kuit.baemin.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * JdbcTemplate 사용
 */
@Slf4j
@Repository
public class UserRepositoryV6 {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryV6(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User save(User user) {
        String insertSql = "INSERT INTO users (nickname, password, email, phone_number) " +
                "VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhoneNumber());
            return pstmt;
        }, keyHolder);

        return findByUserId(keyHolder.getKey().longValue());
    }

    public User findByNickname(String nickname) {
        String selectSql = "SELECT * FROM users WHERE nickname = ?";
        return jdbcTemplate.queryForObject(selectSql, userRowMapper(), nickname);
    }

    public User findByUserId(Long userId) {
        String selectSql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(selectSql, userRowMapper(), userId);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .userId(rs.getLong("user_id"))
                .nickname(rs.getString("nickname"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .status(rs.getString("status"))
                .createdAt(rs.getTimestamp("created_at"))
                .updatedAt(rs.getTimestamp("updated_at"))
                .build();
    }

    public User update(Long userId, User user) {
        String updateSql = "UPDATE users SET nickname = ?, password = ?, email = ?, phone_number = ? WHERE user_id = ?";

        int row = jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(updateSql);
            pstmt.setString(1, user.getNickname());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setLong(5, userId);
            return pstmt;
        });

        if (row == 0)
            throw new EmptyResultDataAccessException(1);

        return findByUserId(userId);
    }
}
