package pl.robocikd.shop.category.model;

import org.springframework.data.domain.Page;
import pl.robocikd.shop.product.model.Product;

public record CategoryProductsDto(Category category, Page<Product> products) {
}
