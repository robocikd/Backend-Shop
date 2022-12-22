package pl.robocikd.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.common.mail.EmailClientService;
import pl.robocikd.shop.common.model.Cart;
import pl.robocikd.shop.common.repository.CartItemRepository;
import pl.robocikd.shop.common.repository.CartRepository;
import pl.robocikd.shop.order.model.Order;
import pl.robocikd.shop.order.model.Payment;
import pl.robocikd.shop.order.model.Shipment;
import pl.robocikd.shop.order.model.dto.OrderDto;
import pl.robocikd.shop.order.model.dto.OrderSummaryDto;
import pl.robocikd.shop.order.repository.OrderRepository;
import pl.robocikd.shop.order.repository.OrderRowRepository;
import pl.robocikd.shop.order.repository.PaymentRepository;
import pl.robocikd.shop.order.repository.ShipmentRepository;

import static pl.robocikd.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.createOrder;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;

    @Transactional
    public OrderSummaryDto placeOrder(OrderDto orderDto, Long userId) {
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        Order newOrder = orderRepository.save(createOrder(orderDto, cart, shipment, payment, userId));
        saveOrderRows(cart, newOrder.getId(), shipment);
        clearOrderCart(orderDto);
        sendConfirmationEmail(newOrder);
        return createOrderSummary(payment, newOrder);
    }

    private void sendConfirmationEmail(Order newOrder) {
        emailClientService.getInstance()
                .send(newOrder.getEmail(), "Twoje zamówienie zostało przyjęte", createEmailMessage(newOrder));
    }

    private void clearOrderCart(OrderDto orderDto) {
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteById(orderDto.getCartId());
    }

    private void saveOrderRows(Cart cart, Long orderId, Shipment shipment) {
        saveProductRows(cart, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveProductRows(Cart cart, Long orderId) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowWithQuantity(orderId, cartItem))
                .peek(orderRowRepository::save)
                .toList();
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(orderId, shipment));
    }


}
