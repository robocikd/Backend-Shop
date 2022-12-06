package pl.robocikd.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.common.model.CartItem;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Long countByCartId(Long cartId);

    @Transactional
    @Modifying
    @Query("delete from CartItem c where c.cartId = ?1")
    void deleteByCartId(Long id);

    @Transactional
    @Modifying
    @Query("delete from CartItem c where c.cartId in ?1")
    void deleteAllByCartIdIn(List<Long> ids);
}
