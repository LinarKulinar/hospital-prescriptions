package com.haulmont.testtask.model;

/**
 * Данный класс описывает сущность "Доктор"
 */
public class Doctor {
    public final static Doctor SAMPLE_DOCTOR = new Doctor(
            "Иванова",
            "Ирина",
            "Ивановна",
            "Терапевт");

    private long doctorId;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String specialization;
    private boolean isCanDelete = true; //true, если нет строчек таблице в Prescription


    public Doctor(long doctorId, String lastName, String firstName, String patronymic, String specialization, boolean isCanDelete) {
        this.doctorId = doctorId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.specialization = specialization;
        this.isCanDelete = isCanDelete;
    }

    public Doctor(String lastName, String firstName, String patronymic, String specialization) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.specialization = specialization;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "id = " + doctorId +
                ", name = " + lastName + " "
                + firstName + " "
                + patronymic + "\t"
                + specialization;
    }

    public boolean isCanDelete() {
        return isCanDelete;
    }
}
