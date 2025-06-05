package kuit.baemin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class Menu {
    private Long menu_id;
    private String menu_name;
    private Long price;
    private Long recommendation_count;
    private String option;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Long restaurant_id;
}
