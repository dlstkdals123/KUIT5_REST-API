package kuit.baemin.controller;

import kuit.baemin.domain.Menu;
import kuit.baemin.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public List<Menu> getMenus() {
        return menuService.findAll();
    }
}
