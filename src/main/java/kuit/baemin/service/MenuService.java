package kuit.baemin.service;

import kuit.baemin.domain.Menu;
import kuit.baemin.dto.MenuRequest;
import kuit.baemin.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 * MemberRepository 인터페이스 의존
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public Menu save(MenuRequest menuRequest) {
        return menuRepository.save(Menu.builder()
                        .menu_name(menuRequest.getMenu_name())
                        .price(menuRequest.getPrice())
                        .recommendation_count(menuRequest.getRecommendation_count())
                        .option(menuRequest.getOption())
                        .restaurant_id(menuRequest.getRestaurant_id())
                        .build());
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
