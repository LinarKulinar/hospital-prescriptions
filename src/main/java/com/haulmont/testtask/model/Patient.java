package com.haulmont.testtask.model;

import java.io.Serializable;

/**
 * Данный класс описывает сущность "Пациент"
 */
@SuppressWarnings("serial")
public class Patient implements Serializable, Cloneable {
    public final static Patient SAMPLE_PATIENT = new Patient(
            "Иванов",
            "Иван",
            "Иванович",
            "+7(999)999-99-99");

    private long patientId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;
    private boolean isCanDelete = true; //true, если нет строчек таблице в Prescription


    public Patient(long patientId, String lastName, String firstName, String patronymic, String phoneNumber, boolean isCanDelete) {
        this.patientId = patientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.isCanDelete = isCanDelete;
    }

    public Patient(String lastName, String firstName, String patronymic, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
    }


    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return lastName + " "
                + firstName + " "
                + patronymic;
    }

//    public String getfullName() {
//        return lastName + " "
//                + firstName + " "
//                + patronymic;
//    }

    public boolean isCanDelete() {
        return isCanDelete;
    }
}
