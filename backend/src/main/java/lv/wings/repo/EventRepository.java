package lv.wings.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import lv.wings.model.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
	List<Event> findAllByOrderByIdDesc();

	List<Event> findAllByOrderByIdAsc();

	@Query("""
			SELECT DISTINCT e
			FROM Event e
			JOIN e.translations t
			WHERE (:q IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%'))
			                OR LOWER(t.description) LIKE LOWER(CONCAT('%', :q, '%')))
			  AND (:start IS NULL OR e.startDate >= :start)
			  AND (:end IS NULL OR e.endDate <= :end)
			""")
	Page<Event> findAllForAdmin(
			Pageable pageable,
			@Param("q") String q,
			@Param("start") LocalDate start,
			@Param("end") LocalDate end);
}
