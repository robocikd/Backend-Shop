package pl.robocikd.shop.admin.order.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.robocikd.shop.common.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminOrderDto {
    private Long id;
    private LocalDateTime placeDate;
    private OrderStatus orderStatus;
    private BigDecimal grossValue;
}
