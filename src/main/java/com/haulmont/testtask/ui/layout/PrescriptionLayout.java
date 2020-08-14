package com.haulmont.testtask.ui.layout;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dao.PatientJdbcDAO;
import com.haulmont.testtask.dao.PrescriptionJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.ui.form.PrescriptionForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.SimpleDateFormat;
import java.util.List;


public class PrescriptionLayout extends VerticalLayout {
    private DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private PatientJdbcDAO patientService = hsqlDB.getPatientDao();
    private DoctorJdbcDAO doctorService = hsqlDB.getDoctorDao();
    private PrescriptionJdbcDAO prescriptionService = hsqlDB.getPrescriptionDao();
    private Grid<Prescription> grid = new Grid<>(Prescription.class);
    //Это ссылка на форму, которая выкидывается при редактировании/удалении
    private PrescriptionForm prescriptionForm = new PrescriptionForm(this);


    /**
     * инициализируем и возвращаем кнопку редактирования для {@link Prescription}
     */
    private Button buildEditButton(Prescription p) {
        Button button = new Button(VaadinIcons.PENCIL);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            prescriptionForm.setPrescription(p); // передаем в окно рецепт, с которым будем работать
            prescriptionForm.showForm();
        });
        return button;
    }

    /**
     * инициализируем и возвращаем кнопку удаления для {@link Prescription}
     */
    private Button buildDeleteButton(Prescription p) {
        Button button = new Button(VaadinIcons.CLOSE);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(e -> {
            prescriptionService.delete(p);
            updateList();
        });
        return button;
    }

    /**
     * Обьект последнего столбца в Grid, тут пишется номер телефона, кнопки редактирования и удаления {@link Prescription}
     */
    private HorizontalLayout horizontalLayout(Prescription p) {
        HorizontalLayout hl = new HorizontalLayout(); //тут поместим кнопки и сдвинем максиально вправо
        hl.addComponent(buildEditButton(p)); // кнопка редактирования Prescription
        hl.addComponent(buildDeleteButton(p)); // кнопка удаления Prescription
        hl.setMargin(false);
        hl.setWidthUndefined();
        return hl;
    }

    /**
     * Тут мы получаем ФИО пациентов для вставки в grid
     */
    private Label getFullNamePatient(Prescription p) {
        return new Label(patientService.getById(p.getPatientId()).toString());
    }

    /**
     * Тут мы получаем ФИО врачей для вставки в grid
     */
    private Label getFullNameDoctor(Prescription p) {
        return new Label(doctorService.getById(p.getDoctorId()).toString());
    }

    /**
     * Тут мы форматируем дату и возвращаем, обернутую в {@Label}
     */
    private Label getRussianDateFormat(Prescription p) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return new Label(format.format(p.getDateCreat()));
    }

    /**
     * Элемент разметки, в котором отображается в {@link Grid} объекты {@link Prescription}
     */
    public PrescriptionLayout() {

        grid.setColumns("description"/*, "firstName", "patronymic"*/);
        grid.getColumn("description").setCaption("Описание");
        grid.addComponentColumn(this::getFullNamePatient).setCaption("ФИО пациента");
        grid.addComponentColumn(this::getFullNameDoctor).setCaption("ФИО доктора");
//        grid.addColumn("dateCreat").setCaption("Дата выдачи");
        grid.addComponentColumn(this::getRussianDateFormat).setCaption("Дата выдачи");
        grid.addColumn("validityPeriod").setCaption("Срок действия");
        grid.addColumn("priority").setCaption("Приоритет");
        grid.addComponentColumn(this::horizontalLayout).setSortable(false).setWidth(138);
//        grid.setFrozenColumnCount(6);
        grid.setSizeFull();


        addComponents(grid);
        updateList();
    }

    /**
     * Метод вызывается для повторной отрисовки пациентов в grid
     */
    public void updateList() {
        List<Prescription> prescriptions = prescriptionService.getAll();
        grid.setItems(prescriptions);
    }

    public void addPrescription() {
        prescriptionForm.addPrescription();
        prescriptionForm.showForm();
    }

}
