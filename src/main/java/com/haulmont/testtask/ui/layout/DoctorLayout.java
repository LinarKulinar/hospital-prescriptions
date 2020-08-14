package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.ui.form.DoctorForm;
import com.haulmont.testtask.ui.form.DoctorStatisticForm;
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
    //Это ссылка на форму, которая выкидывается при выводе статистики
    private DoctorStatisticForm doctorStatisticForm = new DoctorStatisticForm(this);


    /**
     * инициализируем и возвращаем кнопку статистики для {@link Doctor}
     */
    private Button buildStatisticButton(Doctor d) {
        Button button = new Button(VaadinIcons.BAR_CHART_H);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            doctorStatisticForm.setDoctor(d); // передаем в окно доктора, с которым будем работать
            doctorStatisticForm.showForm();
        });
        return button;
    }

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
        HorizontalLayout hl = new HorizontalLayout(); //тут поместим кнопки и сдвинем максиально вправо
        hl.addComponent(buildStatisticButton(d));
        hl.addComponent(buildEditButton(d)); // кнопка редактирования Prescription
        hl.addComponent(buildDeleteButton(d)); // кнопка удаления Prescription
        hl.setMargin(false);
        hl.setWidthUndefined();
        return hl;
    }

    /**
     * Элемент разметки, в котором отображается в {@link Grid} объекты {@link Doctor}
     */
    public DoctorLayout() {

        grid.setColumns("lastName", "firstName", "patronymic", "specialization");
        grid.getColumn("lastName").setCaption("Фамилия");
        grid.getColumn("firstName").setCaption("Имя").setSortable(false);
        grid.getColumn("patronymic").setCaption("Отчество").setSortable(false);
        grid.getColumn("specialization").setCaption("Специальность").setSortable(false);
        grid.addComponentColumn(this::horizontalLayout).setCaption("").setSortable(false).setWidth(195);
        ;
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
