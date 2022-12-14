package pl.robocikd.shop.admin.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.admin.order.model.dto.AdminOrderStatisticsDto;
import pl.robocikd.shop.admin.order.service.AdminOrderStatisticsService;

@RestController
@RequestMapping("/admin/orders/stats")
@RequiredArgsConstructor
public class AdminOrderStatusController {

    private final AdminOrderStatisticsService adminOrderStatisticsService;

    @GetMapping
    public AdminOrderStatisticsDto getOrderStatistics() {
        return adminOrderStatisticsService.getStatistics();
    }
}
