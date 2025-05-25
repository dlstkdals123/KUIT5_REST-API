package kuit.baemin.repository;

import kuit.baemin.domain.User;
import kuit.baemin.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.*;

/**
 * SQLExceptionTranslator 추가
 */
@Slf4j
public class UserRepositoryV5 implements UserRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator sqlExceptionTranslator;

    public UserRepositoryV5(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    public User save(User user)  {
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
            throw sqlExceptionTranslator.translate("save", sql, e);
        } finally {
            // 커넥션은 종료하지 않음
            close(con, pstmt, null);
        }

    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        DataSourceUtils.releaseConnection(con, dataSource);
    }

}
