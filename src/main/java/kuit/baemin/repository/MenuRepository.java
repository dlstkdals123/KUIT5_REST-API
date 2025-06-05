package kuit.baemin.repository;

import kuit.baemin.domain.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
public class MenuRepository {
    private final JdbcTemplate jdbcTemplate;

    public MenuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Menu save(Menu menu) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO menus (menu_name, price, recommendation_count, `option`, status, restaurant_id) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setString(1, menu.getMenu_name());
            pstmt.setLong(2, menu.getPrice());
            pstmt.setLong(3, menu.getRecommendation_count());
            pstmt.setString(4, menu.getOption());
            pstmt.setLong(5, menu.getRestaurant_id());
            return pstmt;
        }, keyHolder);

        return findByMenuId(keyHolder.getKey().longValue());
    }

    public List<Menu> findAll() {
        return jdbcTemplate.query("SELECT * FROM menus", menuRowMapper());
    }

    private Menu findByMenuId(long menuId) {
        String selectSql = "SELECT * FROM menus WHERE menu_id = ?";
        return jdbcTemplate.queryForObject(selectSql, menuRowMapper(), menuId);
    }

    private RowMapper<Menu> menuRowMapper() {
        return (rs, rowNum) -> Menu.builder()
                .menu_id(rs.getLong("menu_id"))
                .menu_name(rs.getString("menu_name"))
                .price(rs.getLong("price"))
                .recommendation_count(rs.getLong("recommendation_count"))
                .option(rs.getString("option"))
                .status(rs.getString("status"))
                .created_at(rs.getTimestamp("created_at"))
                .updated_at(rs.getTimestamp("updated_at"))
                .restaurant_id(rs.getLong("restaurant_id"))
                .build();
    }
}
