package ru.skilup.crowd.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skilup.crowd.components.EmployeeEditor;
import ru.skilup.crowd.domain.Employee;
import ru.skilup.crowd.repo.EmployeeRepo;

@Route
public class MainView extends VerticalLayout {
    private final EmployeeRepo employeeRepo;
    private Grid<Employee> grid = new Grid<>(Employee.class);
    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private HorizontalLayout toolBar = new HorizontalLayout(filter, addNewBtn);

    private final EmployeeEditor employeeEditor;

    @Autowired
    public MainView(EmployeeRepo employeeRepo, EmployeeEditor editor) {
        this.employeeRepo = employeeRepo;
        this.employeeEditor = editor;

        add(toolBar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showEmployee(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editEmployee(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editEmployee(new Employee()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showEmployee(filter.getValue());
        });

        showEmployee("");

    }

    private void showEmployee(String name) {
        if (name.isEmpty()) {
            grid.setItems(employeeRepo.findAll());
        } else {
            grid.setItems(employeeRepo.findByName(name));
        }
    }
}
