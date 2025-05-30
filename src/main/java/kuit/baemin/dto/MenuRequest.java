package kuit.baemin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MenuRequest {

    @JsonCreator
    public MenuRequest(
            @JsonProperty("menu_name") String menu_name,
            @JsonProperty("price") Long price,
            @JsonProperty("recommendation_count") Long recommendation_count,
            @JsonProperty("option") String option,
            @JsonProperty("restaurant_id") Long restaurant_id) {
        this.menu_name = menu_name;
        this.price = price;
        this.recommendation_count = recommendation_count;
        this.option = option;
        this.restaurant_id = restaurant_id;
    }

    private String menu_name;
    private Long price;
    private Long recommendation_count;
    private String option;
    private Long restaurant_id;
}
