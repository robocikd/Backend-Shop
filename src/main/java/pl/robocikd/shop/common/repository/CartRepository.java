package pl.robocikd.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.robocikd.shop.common.model.Cart;

import java.time.LocalDateTime;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.created < ?1")
    List<Cart> findByCreatedLessThan(LocalDateTime date);

    @Transactional
    @Modifying
    @Query("delete from Cart c where c.id in ?1")
    void deleteAllByIdIn(List<Long> ids);
}
