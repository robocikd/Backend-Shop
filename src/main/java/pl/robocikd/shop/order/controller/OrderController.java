package pl.robocikd.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.order.model.dto.InitOrderDto;
import pl.robocikd.shop.order.model.dto.OrderDto;
import pl.robocikd.shop.order.model.dto.OrderListDto;
import pl.robocikd.shop.order.model.dto.OrderSummaryDto;
import pl.robocikd.shop.order.service.OrderService;
import pl.robocikd.shop.order.service.PaymentService;
import pl.robocikd.shop.order.service.ShipmentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;

    @PostMapping
    public OrderSummaryDto placeOrder(@RequestBody @Valid OrderDto orderDto, @AuthenticationPrincipal Long userId) {
        return orderService.placeOrder(orderDto, userId);
    }

    @GetMapping("/initData")
    public InitOrderDto initData() {
        return InitOrderDto.builder()
                .shipments(shipmentService.getShipments())
                .payments(paymentService.getPayments())
                .build();
    }

    @GetMapping
    public List<OrderListDto> getOrders(@AuthenticationPrincipal Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Brak użytkownika");
        }
        return orderService.getOrdersForCustomer(userId);
    }
}
