package hung.tran.junit.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hung.tran.junit.model.Widget;
import hung.tran.junit.service.WidgetService;

import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;

@RestController
public class WidgetRestController {

	private static final Logger logger = LogManager.getLogger(WidgetRestController.class);
	
	@Autowired
	private WidgetService widgetService;
	
	@GetMapping("/rest/widget/{id}")
	public ResponseEntity<?> getWidget(@PathVariable Long id) {
		return widgetService.findById(id)
                .map(widget -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(widget.getVersion()))
                                .location(new URI("/rest/widget/" + widget.getId()))
                                .body(widget);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/rest/widgets")
	public ResponseEntity<List<Widget>> getWidgets(){
		try {
			return ResponseEntity
					.ok()
					.location(new URI("/rest/widgets"))
					.body(widgetService.findAll());
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/rest/widget")
	public ResponseEntity<Widget> createWidget(@RequestBody Widget widget){
		logger.info("Received widget: name: " + widget.getName() + ", description: " + widget.getDescription());
		Widget newWidget = widgetService.save(widget);
		
		try {
			return ResponseEntity
					.created(new URI("/rest/widget/" + newWidget.getId()))
					.eTag(Integer.toString(newWidget.getVersion()))
					.body(newWidget);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
