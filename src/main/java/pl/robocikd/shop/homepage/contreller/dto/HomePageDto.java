package pl.robocikd.shop.homepage.contreller.dto;

import pl.robocikd.shop.common.model.Product;

import java.util.List;

public record HomePageDto(List<Product> saleProducts) {
}
