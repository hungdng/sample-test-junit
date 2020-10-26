package hung.tran.junit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import hung.tran.junit.model.Widget;
import hung.tran.junit.repository.WidgetRepository;

@Service
public class WidgetServiceImpl implements WidgetService {

	@Autowired
	private WidgetRepository repository;
	
	@Override
	public Optional<Widget> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Widget> findAll() {
		return Lists.newArrayList(repository.findAll());
	}

	@Override
	public Widget save(Widget widget) {
		widget.setVersion(widget.getVersion() + 1);
		return repository.save(widget);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}

}
