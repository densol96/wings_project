package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import lv.wings.model.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
	List<Event> findAllByOrderByIdDesc();

	List<Event> findAllByOrderByIdAsc();
}
