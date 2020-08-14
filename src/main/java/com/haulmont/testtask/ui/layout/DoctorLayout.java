package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.ui.form.DoctorForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;


public class DoctorLayout extends VerticalLayout {
    private DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private DoctorJdbcDAO doctorService = hsqlDB.getDoctorDao();
    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    //Это ссылка на форму, которая выкидывается при редактировании/удалении
    private DoctorForm doctorForm = new DoctorForm(this);


    /**
     * инициализируем и возвращаем кнопку редактирования для {@link Doctor}
     */
    private Button buildEditButton(Doctor d) {
        Button button = new Button(VaadinIcons.PENCIL);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            doctorForm.setDoctor(d); // передаем в окно доктора, с которым будем работать
            doctorForm.showForm();
        });
        return button;
    }

    /**
     * инициализируем и возвращаем кнопку удаления для {@link Doctor}
     */
    private Button buildDeleteButton(Doctor d) {
        Button button = new Button(VaadinIcons.CLOSE);
        button.setEnabled(d.isCanDelete()); // выключаем кнопку, если нельзя удалить этого пользователя
        if (!d.isCanDelete())  // напишем информационное сообщение
            button.setDescription("У доктора есть выписанные рецепты.\n" +
                    "Для удаления доктора необходимо\n" +
                    "удалить все выписанные им рецепты из базы данных.");
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            doctorService.delete(d);
            updateList();
        });
        return button;
    }

    /**
     * Обьект последнего столбца в Grid, тут пишется номер телефона, кнопки редактирования и удаления {@link Doctor}
     */
    private HorizontalLayout horizontalLayout(Doctor d) {
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(new Label(d.getSpecialization())); // тут пишем специализацию Doctor

        HorizontalLayout hlInner = new HorizontalLayout(); //тут поместим кнопки и сдвинем максиально вправо
        hlInner.addComponent(buildEditButton(d)); // кнопка редактирования Doctor
        hlInner.addComponent(buildDeleteButton(d)); // кнопка удаления Doctor

        //добавляем hlInner в hl и делаем так, чтобы он был всегда максимально справа
        hl.addComponent(hlInner);
        hl.setComponentAlignment(hlInner, Alignment.MIDDLE_RIGHT);
        hl.setExpandRatio(hlInner, 1.0f);
        hl.setWidth("100%");

        return hl;
    }

    /**
     * Элемент разметки, в котором отображается в {@link Grid} объекты {@link Doctor}
     */
    public DoctorLayout() {

        grid.setColumns("lastName", "firstName", "patronymic");
        grid.getColumn("lastName").setCaption("Фамилия");
        grid.getColumn("firstName").setCaption("Имя").setSortable(false);
        grid.getColumn("patronymic").setCaption("Отчество").setSortable(false);
        grid.addComponentColumn(this::horizontalLayout).setCaption("Специальность").setSortable(false);
        grid.setSizeFull();


        addComponents(grid);
        updateList();
    }

    /**
     * Метод вызывается для повторной отрисовки пациентов в grid
     */
    public void updateList() {
        List<Doctor> doctors = doctorService.getAll();
        grid.setItems(doctors);
    }

    public void addDoctor() {
        doctorForm.addDoctor();
        doctorForm.showForm();
    }

}
