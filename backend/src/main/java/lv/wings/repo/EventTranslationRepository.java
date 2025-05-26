package lv.wings.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import lv.wings.enums.LocaleCode;
import lv.wings.model.translation.EventTranslation;
import lv.wings.repo.base.SearchableRepository;

public interface EventTranslationRepository extends SearchableRepository<EventTranslation> {

    @Modifying
    @Transactional
    @Query("DELETE FROM EventTranslation et WHERE et.entity.id = :eventId")
    void earlyDeleteByEventId(@Param("eventId") Integer eventId);

    boolean existsByTitleAndLocale(String title, LocaleCode locale);
}

