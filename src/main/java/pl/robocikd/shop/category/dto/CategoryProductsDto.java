package pl.robocikd.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.robocikd.shop.common.dto.ProductListDto;
import pl.robocikd.shop.common.model.Category;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
