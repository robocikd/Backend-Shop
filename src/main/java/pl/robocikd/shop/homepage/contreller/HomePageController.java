package pl.robocikd.shop.homepage.contreller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.homepage.contreller.dto.HomePageDto;
import pl.robocikd.shop.homepage.service.HomePageService;

@RestController
@RequiredArgsConstructor
public class HomePageController {
    private final HomePageService homePageService;

    @GetMapping("/homePage")
    @Cacheable("/homePage")
    public HomePageDto getHomePage() {
        return new HomePageDto(homePageService.getSaleProducts());
    }
}
