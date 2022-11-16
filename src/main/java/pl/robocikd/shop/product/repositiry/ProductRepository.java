package pl.robocikd.shop.product.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
