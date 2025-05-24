package kuit.baemin.repository;

import kuit.baemin.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 */
@Slf4j
public class UserRepositoryV3 {

    private final DataSource dataSource;

    public UserRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User save(User user) throws SQLException {
        String sql = "insert into member(email, password, phone_number, nickname, profile_image) " +
                "values (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getPhone_number());
            pstmt.setString(4, user.getNickname());
            pstmt.setString(5, user.getProfile_image());
            pstmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            // 커넥션은 종료하지 않음
            close(con, pstmt, null);
        }

    }

    private Connection getConnection() throws SQLException {
//주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("get connection={} class={}", con, con.getClass());
        return con;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
//주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        DataSourceUtils.releaseConnection(con, dataSource);
    }

}
