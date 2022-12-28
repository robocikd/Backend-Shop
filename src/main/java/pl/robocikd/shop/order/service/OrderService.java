package pl.robocikd.shop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.common.mail.EmailClientService;
import pl.robocikd.shop.common.model.Cart;
import pl.robocikd.shop.common.model.OrderStatus;
import pl.robocikd.shop.common.repository.CartItemRepository;
import pl.robocikd.shop.common.repository.CartRepository;
import pl.robocikd.shop.order.model.Order;
import pl.robocikd.shop.order.model.OrderLog;
import pl.robocikd.shop.order.model.Payment;
import pl.robocikd.shop.order.model.PaymentType;
import pl.robocikd.shop.order.model.Shipment;
import pl.robocikd.shop.order.model.dto.NotificationReceiveDto;
import pl.robocikd.shop.order.model.dto.OrderDto;
import pl.robocikd.shop.order.model.dto.OrderListDto;
import pl.robocikd.shop.order.model.dto.OrderSummaryDto;
import pl.robocikd.shop.order.repository.OrderLogRepository;
import pl.robocikd.shop.order.repository.OrderRepository;
import pl.robocikd.shop.order.repository.OrderRowRepository;
import pl.robocikd.shop.order.repository.PaymentRepository;
import pl.robocikd.shop.order.repository.ShipmentRepository;
import pl.robocikd.shop.order.service.payment.p24.PaymentMethodP24;
import pl.robocikd.shop.security.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static pl.robocikd.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.robocikd.shop.order.service.mapper.OrderListDtoMapper.mapToOrderListDto;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.createOrder;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.robocikd.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderRowRepository orderRowRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;
    private final UserRepository userRepository;
    private final PaymentMethodP24 paymentMethodP24;
    private final OrderLogRepository orderLogRepository;

    @Transactional
    public OrderSummaryDto placeOrder(OrderDto orderDto, Long userId) {
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        Order newOrder = orderRepository.save(createOrder(orderDto, cart, shipment, payment, userId));
        saveOrderRows(cart, newOrder.getId(), shipment);
        clearOrderCart(orderDto);
        sendConfirmationEmail(newOrder);
        String redirectUrl = initPaymentIfNeeded(newOrder);
        return createOrderSummary(payment, newOrder, redirectUrl);
    }

    private String initPaymentIfNeeded(Order newOrder) {
        if (newOrder.getPayment().getType() == PaymentType.P24_ONLINE) {
            return paymentMethodP24.initPayment(newOrder);
        }
        return null;
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

    public List<OrderListDto> getOrdersForCustomer(Long userId) {
        return mapToOrderListDto(orderRepository.findByUserId(userId));
    }

    public Order getOrderByOrderHash(String orderHash) {
        return orderRepository.findByOrderHash(orderHash).orElseThrow();
    }

    @Transactional
    public void receiveNotification(String orderHash, NotificationReceiveDto receiveDto, String remoteAddress) {
        Order order = getOrderByOrderHash(orderHash);
        String status = paymentMethodP24.receiveNotification(order, receiveDto, remoteAddress);
        if (status.equals("success")) {
            OrderStatus oldStatus = order.getOrderStatus();
            order.setOrderStatus(OrderStatus.PAID);
            orderLogRepository.save(OrderLog.builder()
                    .created(LocalDateTime.now())
                    .orderId(order.getId())
                    .note("Opłacono zamównienie przez Przelewy24, id płatności: "
                            + receiveDto.getStatement()
                            + " , zmieniono status z " + oldStatus.getValue() + " na " + order.getOrderStatus().getValue())
                    .build());
        }
    }
}
