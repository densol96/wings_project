package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.NewsletterSubscriber;
import java.util.Optional;
import java.util.UUID;

public interface NewsletterSubscriberRepository extends JpaRepository<NewsletterSubscriber, UUID> {

    boolean existsByEmail(String email);

    Optional<NewsletterSubscriber> findByEmail(String email);
}
