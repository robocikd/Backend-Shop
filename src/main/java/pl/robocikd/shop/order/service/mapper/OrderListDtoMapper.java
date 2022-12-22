package pl.robocikd.shop.order.service.mapper;

import pl.robocikd.shop.order.model.Order;
import pl.robocikd.shop.order.model.dto.OrderListDto;

import java.util.List;

public class OrderListDtoMapper {
    public static List<OrderListDto> mapToOrderListDto(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderListDto(
                        order.getId(),
                        order.getPlaceDate(),
                        order.getOrderStatus().getValue(),
                        order.getGrossValue()))
                .toList();
    }
}
