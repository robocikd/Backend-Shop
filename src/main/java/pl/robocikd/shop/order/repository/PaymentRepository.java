package pl.robocikd.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.order.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
