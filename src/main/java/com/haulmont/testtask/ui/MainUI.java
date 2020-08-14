package com.haulmont.testtask.ui;

import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.ui.layout.DoctorLayout;
import com.haulmont.testtask.ui.layout.PatientLayout;
import com.haulmont.testtask.ui.layout.PrescriptionLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    VerticalLayout layout = new VerticalLayout();
    private final TabSheet tabs = new TabSheet();
    private final DoctorLayout doctorLayout = new DoctorLayout();
    private final PatientLayout patientLayout = new PatientLayout();
    private final PrescriptionLayout prescriptionLayout = new PrescriptionLayout();
    private Window windowWrappingAddButtonPatient;
    private Window windowWrappingAddButtonDoctor;
    private Window windowWrappingAddButtonPrescription;

    private Window getWindowWrappingAddButton(Button.ClickListener listener) {
        // Create a new sub-window with add button
        Window windowWrappingAddButton = new Window("");
        // Set window size.
        windowWrappingAddButton.setHeight("40px");
        windowWrappingAddButton.setWidth("150px");
        windowWrappingAddButton.setClosable(false);
        windowWrappingAddButton.setResizable(false);
        windowWrappingAddButton.setDraggable(false);
        // Set window position
        int screenWidth = UI.getCurrent().getPage().getCurrent().getBrowserWindowWidth();
        int screenHeight = UI.getCurrent().getPage().getCurrent().getBrowserWindowHeight();
        int percentX = (int) (0.82 * screenWidth);
        int percentY = (int) (0.85 * screenHeight);
        windowWrappingAddButton.setPositionX(percentX);
        windowWrappingAddButton.setPositionY(percentY);
        //add button on sub-window
        Button addBtn = new Button(VaadinIcons.PLUS);
        addBtn.addClickListener(listener);
        addBtn.setSizeFull();
        addBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
        windowWrappingAddButton.setContent(addBtn);
        return windowWrappingAddButton;
    }

    /**
     * В данном методе мы скрываем кнопки addBtn, которые подвязаны на нетекущий layout в tabs
     * и отображам только ту кнопку, которая соответствует текущему layout в tabs
     */
    private void setRightAddButton() {
        if (tabs.getSelectedTab() instanceof PatientLayout) {
            windowWrappingAddButtonDoctor.setVisible(false);
            windowWrappingAddButtonPrescription.setVisible(false);
            windowWrappingAddButtonPatient.setVisible(true);
        }
        if (tabs.getSelectedTab() instanceof DoctorLayout) {
            windowWrappingAddButtonPatient.setVisible(false);
            windowWrappingAddButtonPrescription.setVisible(false);
            windowWrappingAddButtonDoctor.setVisible(true);
        }
        if (tabs.getSelectedTab() instanceof PrescriptionLayout) {
            windowWrappingAddButtonPatient.setVisible(false);
            windowWrappingAddButtonDoctor.setVisible(false);
            windowWrappingAddButtonPrescription.setVisible(true);
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        //инициализируем Tabsheet
        patientLayout.setSizeFull();
        tabs.addTab(patientLayout, "Пациенты");
        doctorLayout.setSizeFull();
        tabs.addTab(doctorLayout, "Врачи");
        doctorLayout.setSizeFull();
        tabs.addTab(prescriptionLayout, "Рецепты");
        prescriptionLayout.setSizeFull();

        tabs.setSizeFull();
        tabs.setStyleName(ValoTheme.BUTTON_PRIMARY);
        this.setSizeFull();
        this.setContent(tabs);

        windowWrappingAddButtonPatient = getWindowWrappingAddButton(e -> patientLayout.addPatient());
        UI.getCurrent().addWindow(windowWrappingAddButtonPatient);

        windowWrappingAddButtonDoctor = getWindowWrappingAddButton(e -> doctorLayout.addDoctor());
        UI.getCurrent().addWindow(windowWrappingAddButtonDoctor);

        windowWrappingAddButtonPrescription = getWindowWrappingAddButton(e -> prescriptionLayout.addPrescription());
        UI.getCurrent().addWindow(windowWrappingAddButtonPrescription);

        setRightAddButton(); // отображаем в начальный момент кнопку
        tabs.addSelectedTabChangeListener(e -> setRightAddButton());

    }


}