package com.haulmont.testtask.ui.form;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.PrescriptionPriorityType;
import com.haulmont.testtask.ui.layout.DoctorLayout;
import com.vaadin.ui.*;

import java.util.Map;

public class DoctorStatisticForm extends FormLayout {

    private TextArea info = new TextArea();

    DataBaseFactory hsqlDB = DataBaseFactory.getFactory(DataBaseType.HSQLDB);
    private DoctorJdbcDAO doctorService = hsqlDB.getDoctorDao();
    private Doctor doctor;
    private DoctorLayout layout;

    private Window modalWindow = new Window(); //окно оборачивающее класс DoctorForm


    public DoctorStatisticForm(DoctorLayout layout) {
        this.layout = layout;

        modalWindow.setContent(this);//Это ссылка на форму, которая выкидывается при редактировании/удалении
        modalWindow.center();
        modalWindow.setModal(true);
        modalWindow.setResizable(false);
        info.setRows(6);
        info.setSizeFull();
        info.setWordWrap(true);
        info.setReadOnly(true);
        setSizeUndefined();
        addComponent(info);
        setMargin(true);
        

    }

    /**
     * Данный метод вызывается при нажатии кнопки "Статистика" в DoctorForm
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        setVisible(true);
        modalWindow.setCaption(doctor.toString());
    }

    /**
     * открываем модальное окно
     */
    public void showForm() {
        Map<PrescriptionPriorityType, Long> map = doctorService.getStatistic(doctor);
        long summaryPrescription = 0;

        for (PrescriptionPriorityType type : map.keySet()) { // Считаем всего рецептов
            summaryPrescription += map.get(type);
        }
        StringBuffer str = new StringBuffer(
                "Всего рецептов - " + summaryPrescription + ".\nИз них со статусом:\n");
        for (PrescriptionPriorityType type : map.keySet()) { // Создаем строку для статистики
            str.append("\n" + type + " - " + map.get(type));
            summaryPrescription += map.get(type);
        }
        info.setValue(str.toString());
        UI.getCurrent().addWindow(modalWindow);
    }

}