package hung.tran.junit.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import hung.tran.junit.model.Widget;
import hung.tran.junit.repository.WidgetRepository;

@SpringBootTest
public class WidgetServiceTest {
	
	// https://www.infoworld.com/article/3543268/junit-5-tutorial-part-2-unit-testing-spring-mvc-with-junit-5.html
	// doReturn(VALUE_TO_RETURN).when(MOCK_CLASS_INSTANCE).MOCK_METHOD
	
	@Autowired
	private WidgetService widgetService;
	
	@MockBean
	private WidgetRepository repository;
	
	@Test
	@DisplayName("Test findById Success")
	void testFindById() {
		// Setup our mock repository
		Widget widget = new Widget(1L, "Widget name", "Description", 1);
		doReturn(Optional.of(widget)).when(repository).findById(1l);
		
		// Execute the service call
		Optional<Widget> returnedWidget = widgetService.findById(1l);
		
		// Assert the response
		Assertions.assertTrue(returnedWidget.isPresent(), "Widget was not found");
		Assertions.assertSame(returnedWidget.get(), widget, "The widget returned was not the same as the mock");
	}
	
	@Test
	@DisplayName("Test findById Not Found")
	void testFindByIdNotFound() {
		// Setup our mock repository
		doReturn(Optional.empty()).when(repository).findById(1L);
		
		// Execute the service call
		Optional<Widget> returnWidget = widgetService.findById(1l);
		
		// Assert the response
		Assertions.assertFalse(returnWidget.isPresent(), "Widget should not be found");
	}
	
	@Test
	@DisplayName("Test findAll")
	void testFindAll() {
		// Setup our mock repository
		Widget widget1 = new Widget(1l, "Widget Name", "Description", 1);
		Widget widget2 = new Widget(2l, "Widget 2 Name", "Description 2", 4);
		doReturn(Arrays.asList(widget1, widget2)).when(repository).findAll();
		
		// Execute the service call
		List<Widget> widgets = widgetService.findAll();
		
		// Assert the response
		Assertions.assertEquals(2, widgets.size(), "findAll should return 2 widgets");
	}
	
	@Test
	@DisplayName("Test save widget")
	void testSave() {
		// Setup our mock repository
		Widget widget = new Widget(1l, "Widget Name", "Description", 1);
		doReturn(widget).when(repository).save(any());
		
		// Execute the service call
		Widget returnedWidget = widgetService.save(widget);
		
		// Assert the response
		Assertions.assertNotNull(returnedWidget, "The saved widget should not be null");
		Assertions.assertEquals(2,  returnedWidget.getVersion(), "The version should be incremented");
	}

}
