package pl.robocikd.shop.cart.controller.mapper;

import pl.robocikd.shop.cart.controller.dto.CartSummaryDto;
import pl.robocikd.shop.cart.controller.dto.CartSummaryItemDto;
import pl.robocikd.shop.cart.controller.dto.SummaryDto;
import pl.robocikd.shop.common.model.Cart;
import pl.robocikd.shop.common.model.CartItem;
import pl.robocikd.shop.common.model.Product;
import pl.robocikd.shop.product.service.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {
    public static CartSummaryDto mapToCartSummaryDto(Cart cart) {
        return CartSummaryDto.builder()
                .id(cart.getId())
                .items(mapToCartSummaryItemDto(cart.getItems()))
                .summary(mapToCartSummaryDto(cart.getItems()))
                .build();
    }


    private static List<CartSummaryItemDto> mapToCartSummaryItemDto(List<CartItem> cartItems) {
        return cartItems.stream().map(CartMapper::mapToCartSummaryItemDto).toList();
    }

    private static CartSummaryItemDto mapToCartSummaryItemDto(CartItem cartItem) {
        return CartSummaryItemDto.builder()
                .id(cartItem.getId())
                .quantity(cartItem.getQuantity())
                .product(mapToProductDto(cartItem.getProduct()))
                .lineValue(calculateLineValue(cartItem))
                .build();
    }

    private static ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getEndPrice())
                .currency(product.getCurrency())
                .image(product.getImage())
                .slug(product.getSlug())
                .build();
    }

    private static BigDecimal calculateLineValue(CartItem cartItem) {
        return cartItem.getProduct().getEndPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }

    private static SummaryDto mapToCartSummaryDto(List<CartItem> items) {
        return SummaryDto.builder()
                .grossValue(sumValues(items))
                .build();
    }

    private static BigDecimal sumValues(List<CartItem> items) {
        return items.stream()
                .map(CartMapper::calculateLineValue)
                .reduce((bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)).orElse(BigDecimal.ZERO);
    }
}
