package pl.robocikd.shop.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.robocikd.shop.common.mail.EmailClientService;
import pl.robocikd.shop.common.mail.FakeEmailService;
import pl.robocikd.shop.common.model.Cart;
import pl.robocikd.shop.common.model.CartItem;
import pl.robocikd.shop.common.model.Product;
import pl.robocikd.shop.common.repository.CartItemRepository;
import pl.robocikd.shop.common.repository.CartRepository;
import pl.robocikd.shop.order.model.OrderStatus;
import pl.robocikd.shop.order.model.Payment;
import pl.robocikd.shop.order.model.PaymentType;
import pl.robocikd.shop.order.model.Shipment;
import pl.robocikd.shop.order.model.dto.OrderDto;
import pl.robocikd.shop.order.model.dto.OrderSummaryDto;
import pl.robocikd.shop.order.repository.OrderRepository;
import pl.robocikd.shop.order.repository.OrderRowRepository;
import pl.robocikd.shop.order.repository.PaymentRepository;
import pl.robocikd.shop.order.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ShipmentRepository shipmentRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRowRepository orderRowRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private EmailClientService emailClientService;
    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldPlaceOrder() {
        // given
        OrderDto orderDto = createOdrerDto();
        when(cartRepository.findById(any())).thenReturn(createCart());
        when(shipmentRepository.findById(any())).thenReturn(createShipment());
        when(paymentRepository.findById(any())).thenReturn(createPayment());
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(emailClientService.getInstance()).thenReturn(new FakeEmailService());
        // when
        OrderSummaryDto orderSummaryDto = orderService.placeOrder(orderDto);
        // then
        assertThat(orderSummaryDto).isNotNull();
        assertThat(orderSummaryDto.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(orderSummaryDto.getGrossValue()).isEqualTo(new BigDecimal("36.22"));
        assertThat(orderSummaryDto.getPayment().getType()).isEqualTo(PaymentType.BANK_TRANSFER);
        assertThat(orderSummaryDto.getPayment().getName()).isEqualTo("test payment");
        assertThat(orderSummaryDto.getPayment().getId()).isEqualTo(1L);
    }

    private Optional<Payment> createPayment() {
        return Optional.of(
                Payment.builder()
                        .id(1L)
                        .name("test payment")
                        .type(PaymentType.BANK_TRANSFER)
                        .defaultPayment(true)
                        .build()
        );
    }

    private Optional<Shipment> createShipment() {
        return Optional.of(
                Shipment.builder()
                        .id(2L)
                        .price(new BigDecimal("14.00"))
                        .build()
        );
    }

    private Optional<Cart> createCart() {
        return Optional.of(Cart.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .items(createItems())
                .build());
    }

    private List<CartItem> createItems() {
        return List.of(
                CartItem.builder()
                        .id(1L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(1L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build(),
                CartItem.builder()
                        .id(2L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(2L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build());
    }

    private OrderDto createOdrerDto() {
        return OrderDto.builder()
                .firstname("firstname")
                .lastname("lastname")
                .street("street")
                .zipcode("zipcode")
                .city("city")
                .email("email@wp.pl")
                .cartId(1L)
                .shipmentId(2L)
                .paymentId(3L)
                .build();
    }
}