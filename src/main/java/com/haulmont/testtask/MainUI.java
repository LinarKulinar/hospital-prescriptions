package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
//        VerticalLayout layout = new VerticalLayout();
//        layout.setSizeFull();
//        layout.setMargin(true);
//
//        layout.addComponent(new Label("Main UI"));
//
//        setContent(layout);


//        Grid<Person> grid = new Grid<>(Person.class);
//        grid.setColumns("firstName", "lastName");
//        grid.setColumnReorderingAllowed(true);
//        grid.setItemDetailsRenderer(new ComponentRenderer<>(person -> {
//            VerticalLayout layout = new VerticalLayout();
//            layout.add(new Label("City: " + person.getCity()));
//            layout.add(new Label("Year of birth: " + person.getBirthDate().getYear()));
//            return layout;
//        }));
//        grid.setItems(collectionOfPersons);
    }
}