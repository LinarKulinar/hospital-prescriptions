package com.haulmont.testtask.model;

import com.haulmont.testtask.dao.PatientJdbcDAO;

import java.sql.Date;

/**
 * Данный класс описывает сущность "Рецепты"
 */
public class Prescription {
    public final static Prescription SAMPLE_PRESCRIPTION = new Prescription(
            "Лазолван",
            0,
            0,
            Date.valueOf("2020-04-05"),
            30,
            PrescriptionPriorityType.Нормальный);

    private long prescriptionId;
    private String description;
    private long patientId;
    private long doctorId;
    private Date dateCreat;
    private long validityPeriod;
    private PrescriptionPriorityType priority;


    public Prescription(long prescriptionId, String description, long patientId, long doctorId,
                        Date dateCreat, long validityPeriod, PrescriptionPriorityType priority) {
        this.prescriptionId = prescriptionId;
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateCreat = dateCreat;
        this.validityPeriod = validityPeriod;
        this.priority = priority;
    }

    public Prescription(String description, long patientId, long doctorId,
                        Date dateCreat, long validityPeriod, PrescriptionPriorityType priority) {
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateCreat = dateCreat;
        this.validityPeriod = validityPeriod;
        this.priority = priority;
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(Date dateCreat) {
        this.dateCreat = dateCreat;
    }

    public long getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(long validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public PrescriptionPriorityType getPriority() {
        return priority;
    }

    public void setPriority(PrescriptionPriorityType priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "id = " + prescriptionId + ", "
                + "name = " + description + ",\t"
                + "patient id = " + patientId + ", "
                + "doctor id = " + doctorId + ",\t"
                + "date creat = " + dateCreat + ", "
                + "validity period = " + validityPeriod + ", "
                + "priority = " + priority.toString();
    }

    public String getDescriptionAndValidityPeriod() {
        return description + " " + validityPeriod;

    }

}
