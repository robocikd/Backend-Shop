package pl.robocikd.shop.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.robocikd.shop.common.model.OrderStatus;
import pl.robocikd.shop.order.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderSummaryDto {
    private Long id;
    private LocalDateTime placeDate;
    private OrderStatus status;
    private BigDecimal grossValue;
    private Payment payment;
    private String redirectUrl;
}
