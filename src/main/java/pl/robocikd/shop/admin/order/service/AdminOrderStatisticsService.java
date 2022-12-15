package pl.robocikd.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.robocikd.shop.admin.order.model.AdminOrder;
import pl.robocikd.shop.admin.order.model.AdminOrderStatus;
import pl.robocikd.shop.admin.order.model.dto.AdminOrderStatisticsDto;
import pl.robocikd.shop.admin.order.repositor.AdminOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AdminOrderStatisticsService {
    private final AdminOrderRepository adminOrderRepository;

    public AdminOrderStatisticsDto getStatistics() {
        LocalDateTime from = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = LocalDateTime.now();
        List<AdminOrder> adminOrders = adminOrderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(
                from,
                to,
                AdminOrderStatus.COMPLETED
        );
        TreeMap<Integer, AdminOrderStatsValue> result = new TreeMap<>();
        for (int i = from.getDayOfMonth(); i <= to.getDayOfMonth(); i++) {
            result.put(i, aggregateValues(i, adminOrders));
        }

        List<BigDecimal> salesList = result.values().stream().map(o -> o.sales).toList();
        List<Long> orderList = result.values().stream().map(o -> o.orders).toList();

        return AdminOrderStatisticsDto.builder()
                .labels(result.keySet().stream().toList())
                .sales(salesList)
                .orders(orderList)
                .ordersNo(orderList.stream().reduce(Long::sum).orElse(0L))
                .salesSum(salesList.stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                .build();
    }

    private AdminOrderStatsValue aggregateValues(int i, List<AdminOrder> adminOrders) {
        BigDecimal totalValue = BigDecimal.ZERO;
        Long orderNo = 0L;
        for (AdminOrder order : adminOrders) {
            if (i == order.getPlaceDate().getDayOfMonth()) {
                totalValue = totalValue.add(order.getGrossValue());
                orderNo++;
            }
        }
        return new AdminOrderStatsValue(totalValue, orderNo);
    }

    private record AdminOrderStatsValue(BigDecimal sales, Long orders) {
    }
}
