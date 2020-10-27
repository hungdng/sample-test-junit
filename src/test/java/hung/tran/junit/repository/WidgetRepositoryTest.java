package hung.tran.junit.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.google.common.collect.Lists;

import hung.tran.junit.model.Widget;

@ExtendWith(DBUnitExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class WidgetRepositoryTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private WidgetRepository repository;
	
	public ConnectionHolder getConnectionHolder() {
		return () -> dataSource.getConnection();
	}
	
    @Test
    @DataSet("widgets.yml")
    void testFindAll() {
        List<Widget> widgets = Lists.newArrayList(repository.findAll());
        Assertions.assertEquals(2, widgets.size(), "Expected 2 widgets in the database");
    }
    
    @Test
    @DataSet("widgets.yml")
    void testFindByIdSuccess() {
        Optional<Widget> widget = repository.findById(1L);
        Assertions.assertTrue(widget.isPresent(), "We should find a widget with ID 1");

        Widget w = widget.get();
        Assertions.assertEquals(1, w.getId(), "The widget ID should be 1");
        Assertions.assertEquals("Widget 1", w.getName(), "Incorrect widget name");
        Assertions.assertEquals("This is widget 1", w.getDescription(), "Incorrect widget description");
        Assertions.assertEquals(1, w.getVersion(), "Incorrect widget version");
    }
    
    @Test
    @DataSet("widgets.yml")
    void testFindByIdNotFound() {
        Optional<Widget> widget = repository.findById(3L);
        Assertions.assertFalse(widget.isPresent(), "A widget with ID 3 should not be found");
    }

    @Test
    @DataSet("widgets.yml")
    void testFindWidgetsWithNameLike() {
        List<Widget> widgets = repository.findWidgetsWithNameLike("Widget%");
        Assertions.assertEquals(2, widgets.size());
    }

}
