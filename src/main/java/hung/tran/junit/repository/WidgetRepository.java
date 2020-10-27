package hung.tran.junit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import hung.tran.junit.model.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Long> {
	@Query(value = "SELECT w FROM Widget w WHERE w.name LIKE ?1")
    List<Widget> findWidgetsWithNameLike(String name);
}
