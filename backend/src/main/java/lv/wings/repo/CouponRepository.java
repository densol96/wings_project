package lv.wings.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Optional<Coupon> findByCodeIgnoreCase(String code);
}
