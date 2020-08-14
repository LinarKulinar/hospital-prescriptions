package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.ui.layout.DoctorLayout;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DoctorForm extends FormLayout {

    private TextField lastName = new TextField("Фамилия");
    private TextField firstName = new TextField("Имя");
    private TextField patronymic = new TextField("Отчество");
    private TextField specialization = new TextField("Специализация");
    private Button saveButton = new Button("ОК");
    private Button addButton = new Button("ОК");
    private Button cancelButton = new Button("Отменить");

    DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private DoctorJdbcDAO doctorService = hsqlDB.getDoctorDao();
    private Doctor doctor;
    private DoctorLayout layout;

    private Window modalWindow = new Window(); //окно оборачивающее класс DoctorForm

    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorForm(DoctorLayout layout) {
        this.layout = layout;

        modalWindow.setContent(this);//Это ссылка на форму, которая выкидывается при редактировании/удалении
        modalWindow.center();
        modalWindow.setModal(true);
        modalWindow.setResizable(false);

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(saveButton, addButton, cancelButton);

        addComponents(lastName, firstName, patronymic, specialization, buttons);
        setMargin(true);

        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.setClickShortcut(KeyCode.ENTER);
        addButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        addButton.setClickShortcut(KeyCode.ENTER);

        binder.forField(lastName)
                .withValidator(new RegexpValidator(
                        "Введите фамилию. Фамилия должна начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я]*)"))
                .bind(Doctor::getLastName, Doctor::setLastName);
        binder.forField(firstName)
                .withValidator(new RegexpValidator(
                        "Введите имя. Имя должно начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я]*)"))
                .bind(Doctor::getFirstName, Doctor::setFirstName);
        binder.forField(patronymic)
                .withValidator(new RegexpValidator(
                        "Введите отчество. Отчество должно начинаться с заглавной буквы",
                        "([А-Я]{1})([а-я]*)"))
                .bind(Doctor::getPatronymic, Doctor::setPatronymic);
        binder.forField(specialization)
                .withValidator(new RegexpValidator(
                        "Введите специализацию. Она должна начинаться с заглавной буквы", "([А-Я]{1})([а-я]*)"))
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);

        saveButton.addClickListener(e -> this.save());
        addButton.addClickListener(e -> this.add());
        cancelButton.addClickListener(e -> modalWindow.close());
    }

    /**
     * Данный метод вызывается при нажатии кнопки "Редактировать" в DoctorForm
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        binder.setBean(doctor);
        setVisible(true);
        firstName.selectAll();
        //делаем видимой нужную кнопку
        addButton.setVisible(false);
        saveButton.setVisible(true);
        modalWindow.setCaption("Редактировать врача");
    }

    /**
     * Данный метод вызывается при нажатии кнопки "Добавить" в DoctorForm
     */
    public void addDoctor() {
        this.doctor = Doctor.SAMPLE_DOCTOR;
        binder.setBean(doctor);
        setVisible(true);
        firstName.selectAll();
        //делаем видимой нужную кнопку
        saveButton.setVisible(false);
        addButton.setVisible(true);
        modalWindow.setCaption("Добавить врача");
    }

    /**
     * Метод вызывется при нажатии кнопки "ОК" в {@link DoctorForm} при редактировании {@link Doctor}
     */
    private void save() {
        if (binder.validate().isOk()) {
            doctorService.update(doctor);
            layout.updateList();
            modalWindow.close();
        }
    }

    /**
     * Метод вызывется при нажатии кнопки "ОК" в {@link DoctorForm} при добавлении нового {@link Doctor}
     */
    private void add() {
        if (binder.validate().isOk()) {
            doctorService.insert(doctor);
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