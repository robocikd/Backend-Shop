package pl.robocikd.shop.category.model;

import org.springframework.data.domain.Page;
import pl.robocikd.shop.product.controller.dto.ProductListDto;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
