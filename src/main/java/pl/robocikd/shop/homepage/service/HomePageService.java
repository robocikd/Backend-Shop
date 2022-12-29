package pl.robocikd.shop.homepage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.robocikd.shop.common.model.Product;
import pl.robocikd.shop.common.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {
    private final ProductRepository productRepository;

    public List<Product> getSaleProducts() {
        return productRepository.findTop10BySalePriceIsNotNull();
    }
}
