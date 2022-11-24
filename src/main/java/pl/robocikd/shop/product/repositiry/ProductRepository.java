package pl.robocikd.shop.product.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.product.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);
}
