package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dao.PatientJdbcDAO;
import com.haulmont.testtask.dao.PrescriptionJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.PrescriptionPriorityType;
import com.haulmont.testtask.ui.layout.PrescriptionLayout;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.time.LocalDate;

public class PrescriptionForm extends FormLayout {

    private TextField decriptionField = new TextField("Описание");
    private ComboBox<Patient> patientComboBox = new ComboBox<>("Пациент");
    private ComboBox<Doctor> doctorComboBox = new ComboBox<>("Врач");
    private DateField dateCreatField = new DateField("Дата выдачи");
    private TextField validityPeriodField = new TextField("Срок действия");
    private ComboBox<PrescriptionPriorityType> priorityComboBox = new ComboBox<>("Приоритет");

    private Button saveButton = new Button("ОК");
    private Button addButton = new Button("ОК");
    private Button cancelButton = new Button("Отменить");

    DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private PatientJdbcDAO patientService = hsqlDB.getPatientDao();
    private DoctorJdbcDAO doctorService = hsqlDB.getDoctorDao();
    private PrescriptionJdbcDAO prescriptionService = hsqlDB.getPrescriptionDao();

    private Prescription prescription;
    private PrescriptionLayout layout;

    private Window modalWindow = new Window(); //окно оборачивающее класс PatientForm

    private Binder<Prescription> binder = new Binder<>(Prescription.class);

    /**
     * Тут мы получаем {@link Patient} для вставки в grid
     */
    private Patient getPatientForBind(Prescription p) {
        return patientService.getById(p.getPatientId());
    }

    /**
     * Тут мы из {@link Patient} обновляем индекс в {@link Prescription}
     */
    private void setPatientForBind(Prescription prescription, Patient patient) {
        prescription.setPatientId(patient.getPatientId());
    }

    /**
     * Тут мы получаем {@link Doctor} для вставки в grid
     */
    private Doctor getDoctorForBind(Prescription p) {
        return doctorService.getById(p.getDoctorId());
    }

    /**
     * Тут мы из {@link Doctor} обновляем индекс в {@link Prescription}
     */
    private void setDoctorForBind(Prescription prescription, Doctor doctor) {
        prescription.setDoctorId(doctor.getDoctorId());
    }

    /**
     * конвертируем {@link Date} в {@link LocalDate} из {@link Prescription}
     */
    private LocalDate getDateCreat(Prescription prescription) {
        return prescription.getDateCreat().toLocalDate();
    }

    /**
     * конвертируем {@link LocalDate} в {@link Date} и кладем в {@link Prescription}
     */
    private void setDateCreat(Prescription prescription, LocalDate localDate) {
        prescription.setDateCreat(Date.valueOf(localDate));
    }

    /**
     * Тут мы возвращаем в виде строки validityPeriod из {@link Prescription}
     */
    private String getValidityPeriodForBind(Prescription prescription) {
        return prescription.getValidityPeriod() + "";
    }

    /**
     * Тут мы из строки парсим validityPeriod в {@link Prescription}
     */
    private void setValidityPeriodForBind(Prescription prescription, String s) {
        prescription.setValidityPeriod(Long.parseLong(s));
    }

    /**
     * Тут мы возвращаем {@link PrescriptionPriorityType} из {@link Prescription}
     */
    private PrescriptionPriorityType getPriorityForBind(Prescription prescription) {
        return prescription.getPriority();
    }

    /**
     * Тут мы уставливаем тип {@link Prescription}
     */
    private void setPriorityForBind(Prescription prescription, PrescriptionPriorityType prescriptionPriorityType) {
        prescription.setPriority(prescriptionPriorityType);
    }

    public PrescriptionForm(PrescriptionLayout layout) {
        this.layout = layout;

        modalWindow.setContent(this);//Это ссылка на форму, которая выкидывается при редактировании/удалении
        modalWindow.center();
        modalWindow.setModal(true);
        modalWindow.setResizable(false);

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(saveButton, addButton, cancelButton);

        addComponents(decriptionField, patientComboBox, doctorComboBox, dateCreatField, validityPeriodField, priorityComboBox, buttons);
        setMargin(true);

        patientComboBox.setItems(patientService.getAll()); // заполним пациентами combobox
        doctorComboBox.setItems(doctorService.getAll()); // заполним пациентами combobox
        priorityComboBox.setItems(PrescriptionPriorityType.values());

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(KeyCode.ENTER);
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setClickShortcut(KeyCode.ENTER);

        binder.forField(decriptionField)
                .withValidator(new RegexpValidator(
                        "Введите название лекарства. Оно должно начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я ]*)"))
                .bind(Prescription::getDescription, Prescription::setDescription);
        binder.forField(patientComboBox)
                .withValidator(patient -> patient != null, "Please choose the patient.")
                .bind(this::getPatientForBind, this::setPatientForBind);
        binder.forField(doctorComboBox)
                .withValidator(doctor -> doctor != null, "Please choose the doctor.")
                .bind(this::getDoctorForBind, this::setDoctorForBind);
        binder.forField(dateCreatField)
                .withValidator(localDate -> localDate.compareTo(LocalDate.now()) <= 0,
                        "Please enter date not later than today")
                .bind(this::getDateCreat, this::setDateCreat);
        binder.forField(validityPeriodField)
                .withValidator(new RegexpValidator(
                        "Введите целое число", "(\\d*)"))
                .bind(this::getValidityPeriodForBind, this::setValidityPeriodForBind);
        binder.forField(priorityComboBox)
                .withValidator(prescriptionPriorityType -> prescriptionPriorityType != null,
                        "Please choose the priority type.")
                .bind(this::getPriorityForBind, this::setPriorityForBind);

        saveButton.addClickListener(e -> this.save());
        addButton.addClickListener(e -> this.add());
        cancelButton.addClickListener(e -> modalWindow.close());
    }


    /**
     * Данный метод вызывается при нажатии кнопки "Редактировать" в PatientForm
     */
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
        binder.setBean(prescription);
        setVisible(true);
        decriptionField.selectAll();
        //делаем видимой нужную кнопку
        addButton.setVisible(false);
        saveButton.setVisible(true);
        modalWindow.setCaption("Редактировать рецепт");
    }

    /**
     * Данный метод вызывается при нажатии кнопки "Добавить" в PatientForm
     */
    public void addPrescription() {
        this.prescription = Prescription.SAMPLE_PRESCRIPTION;
        binder.setBean(prescription);
        setVisible(true);
        decriptionField.selectAll();
        //делаем видимой нужную кнопку
        saveButton.setVisible(false);
        addButton.setVisible(true);
        modalWindow.setCaption("Добавить рецепт");
    }

    /**
     * Метод вызывется при нажатии кнопки "ОК" в {@link PrescriptionForm} при редактировании {@link Patient}
     */
    private void save() {
        if (binder.validate().isOk()) {
            prescriptionService.update(prescription);
            layout.updateList();
            modalWindow.close();
        }
    }

    /**
     * Метод вызывется при нажатии кнопки "ОК" в {@link PrescriptionForm} при добавлении нового {@link Patient}
     */
    private void add() {
        if (binder.validate().isOk()) {
            prescriptionService.insert(prescription);
            layout.updateList();
            modalWindow.close();
        }
    }

    /**
     * открываем модальное окно для редактирования/добавления
     */
    public void showForm() {
        UI.getCurrent().addWindow(modalWindow);
    }

}