package pl.robocikd.shop.cart.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.robocikd.shop.product.service.dto.ProductDto;

import java.math.BigDecimal;

@Getter
@Builder
public class CartSummaryItemDto {
    private Long id;
    private int quantity;
    private ProductDto product;
    private BigDecimal lineValue;
}
