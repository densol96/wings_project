package lv.wings.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.enums.Country;
import lv.wings.model.entity.DeliveryPrice;

public interface DeliveryPriceRepository extends JpaRepository<DeliveryPrice, Integer> {
    List<DeliveryPrice> findAllByCountry(Country country);
}
