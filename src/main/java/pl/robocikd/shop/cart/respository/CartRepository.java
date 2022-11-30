package pl.robocikd.shop.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
