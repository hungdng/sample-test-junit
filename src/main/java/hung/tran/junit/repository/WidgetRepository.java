package hung.tran.junit.repository;

import org.springframework.data.repository.CrudRepository;

import hung.tran.junit.model.Widget;

public interface WidgetRepository extends CrudRepository<Widget, Long> {

}
