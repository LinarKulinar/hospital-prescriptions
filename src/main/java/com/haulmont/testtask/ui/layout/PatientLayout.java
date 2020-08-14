package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.PatientJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.form.PatientForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;


public class PatientLayout extends VerticalLayout {
    private DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private PatientJdbcDAO patientService = hsqlDB.getPatientDao();
    private Grid<Patient> grid = new Grid<>(Patient.class);
    //Это ссылка на форму, которая выкидывается при редактировании/удалении
    private PatientForm patientForm = new PatientForm(this);

    /**
     * инициализируем и возвращаем кнопку редактирования для {@link Patient}
     */
    private Button buildEditButton(Patient p) {
        Button button = new Button(VaadinIcons.PENCIL);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            patientForm.setPatient(p); // передаем в окно пациента, с которым будем работать
            patientForm.showForm();
        });
        return button;
    }

    /**
     * инициализируем и возвращаем кнопку удаления для {@link Patient}
     */
    private Button buildDeleteButton(Patient p) {
        Button button = new Button(VaadinIcons.CLOSE);
        button.setEnabled(p.isCanDelete()); // выключаем кнопку, если нельзя удалить этого пользователя
        if (!p.isCanDelete())  // напишем информационное сообщение
            button.setDescription("У пациента есть выписанные рецепты.\n" +
                    "Для удаления пациента необходимо\n" +
                    "удалить все выписанные ему рецепты из базы данных.");
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            patientService.delete(p);
            updateList();
        });
        return button;
    }

    /**
     * Обьект последнего столбца в Grid, тут пишется номер телефона, кнопки редактирования и удаления {@link Patient}
     */
    private HorizontalLayout horizontalLayout(Patient p) {
        HorizontalLayout hl = new HorizontalLayout(); //тут поместим кнопки и сдвинем максиально вправо
        hl.addComponent(buildEditButton(p)); // кнопка редактирования Prescription
        hl.addComponent(buildDeleteButton(p)); // кнопка удаления Prescription
        hl.setMargin(false);
        hl.setWidthUndefined();
        return hl;
    }

    /**
     * Элемент разметки, в котором отображается в {@link Grid} объекты {@link Patient}
     */
    public PatientLayout() {

        grid.setColumns("lastName", "firstName", "patronymic");
        grid.getColumn("lastName").setCaption("Фамилия");
        grid.getColumn("firstName").setCaption("Имя").setSortable(false);
        grid.getColumn("patronymic").setCaption("Отчество").setSortable(false);
        grid.addColumn("phoneNumber").setCaption("Телефон");
        grid.addComponentColumn(this::horizontalLayout).setSortable(false).setWidth(138);;
        grid.setSizeFull();

        addComponents(grid);
        updateList();
    }

    /**
     * Метод вызывается для повторной отрисовки пациентов в grid
     */
    public void updateList() {
        List<Patient> patients = patientService.getAll();
        grid.setItems(patients);
    }

    public void addPatient() {
        patientForm.addPatient();
        patientForm.showForm();
    }

}
