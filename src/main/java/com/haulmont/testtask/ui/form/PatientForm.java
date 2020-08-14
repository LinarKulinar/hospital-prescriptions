package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.PatientJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.ui.layout.PatientLayout;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PatientForm extends FormLayout {

    private TextField lastName = new TextField("Фамилия");
    private TextField firstName = new TextField("Имя");
    private TextField patronymic = new TextField("Отчество");
    private TextField phoneNumber = new TextField("Телефон");
    private Button saveButton = new Button("ОК");
    private Button addButton = new Button("ОК");
    private Button cancelButton = new Button("Отменить");

    DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private PatientJdbcDAO patientService = hsqlDB.getPatientDao();
    private Patient patient;
    private PatientLayout layout;

    private Window modalWindow = new Window(); //окно оборачивающее класс PatientForm

    private Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientForm(PatientLayout layout) {
        this.layout = layout;

        modalWindow.setContent(this);//Это ссылка на форму, которая выкидывается при редактировании/удалении
        modalWindow.center();
        modalWindow.setModal(true);
        modalWindow.setResizable(false);

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(saveButton, addButton, cancelButton);

        addComponents(lastName, firstName, patronymic, phoneNumber, buttons);
        setMargin(true);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(KeyCode.ENTER);
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setClickShortcut(KeyCode.ENTER);

        binder.forField(lastName)
                .withValidator(new RegexpValidator(
                        "Введите фамилию. Фамилия должна начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я]*)"))
                .bind(Patient::getLastName, Patient::setLastName);
        binder.forField(firstName)
                .withValidator(new RegexpValidator(
                        "Введите имя. Имя должно начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я]*)"))
                .bind(Patient::getFirstName, Patient::setFirstName);
        binder.forField(patronymic)
                .withValidator(new RegexpValidator(
                        "Введите отчество. Отчество должно начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я]*)"))
                .bind(Patient::getPatronymic, Patient::setPatronymic);
        binder.forField(phoneNumber)
                .withValidator(new RegexpValidator(
                        "Введите номер телефона в формате +7(XXX)XXX-XX-XX", "\\+7\\((\\d{3})\\)(\\d{3})-(\\d{2})-(\\d{2})"))
                .bind(Patient::getPhoneNumber, Patient::setPhoneNumber);

        saveButton.addClickListener(e -> this.save());
        addButton.addClickListener(e -> this.add());
        cancelButton.addClickListener(e -> modalWindow.close());
    }

    /**
     * Данный метод вызывается при нажатии кнопки "Редактировать" в PatientForm
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
        binder.setBean(patient);
        setVisible(true);
        firstName.selectAll();
        //делаем видимой нужную кнопку
        addButton.setVisible(false);
        saveButton.setVisible(true);
        modalWindow.setCaption("Редактировать пациента");
    }

    /**
     * Данный метод вызывается при нажатии кнопки "Добавить" в PatientForm
     */
    public void addPatient() {
        this.patient = Patient.SAMPLE_PATIENT;
        binder.setBean(patient);
        setVisible(true);
        firstName.selectAll();
        //делаем видимой нужную кнопку
        saveButton.setVisible(false);
        addButton.setVisible(true);
        modalWindow.setCaption("Добавить пациента");
    }

    /**
     * Метод вызывется при нажатии кнопки "ОК" в {@link PatientForm} при редактировании {@link Patient}
     */
    private void save() {
        if (binder.validate().isOk()) {
            patientService.update(patient);
            layout.updateList();
            modalWindow.close();
        }
    }

    /**
     * Метод вызывется при нажатии кнопки "ОК" в {@link PatientForm} при добавлении нового {@link Patient}
     */
    private void add() {
        if (binder.validate().isOk()) {
            patientService.insert(patient);
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