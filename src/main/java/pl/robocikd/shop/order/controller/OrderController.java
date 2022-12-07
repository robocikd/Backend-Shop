package pl.robocikd.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.order.model.dto.InitOrderDto;
import pl.robocikd.shop.order.model.dto.OrderDto;
import pl.robocikd.shop.order.model.dto.OrderSummaryDto;
import pl.robocikd.shop.order.service.OrderService;
import pl.robocikd.shop.order.service.PaymentService;
import pl.robocikd.shop.order.service.ShipmentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;

    @PostMapping
    public OrderSummaryDto placeOrder(@RequestBody @Valid OrderDto orderDto) {
        return orderService.placeOrder(orderDto);
    }

    @GetMapping("/initData")
    public InitOrderDto initData() {
        return InitOrderDto.builder()
                .shipments(shipmentService.getShipments())
                .payments(paymentService.getPayments())
                .build();
    }
}
