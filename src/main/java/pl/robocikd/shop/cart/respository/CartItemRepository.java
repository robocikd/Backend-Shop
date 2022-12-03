package pl.robocikd.shop.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Long countByCartId(Long cartId);
}
