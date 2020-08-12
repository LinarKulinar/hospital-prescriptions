package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Prescription;
import com.haulmont.testtask.model.PrescriptionPriorityType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PrescriptionJdbcDAO implements DAO<Prescription> {
    private Connection connection;

    /**
     * При вызове конструктора необходимо указать в параметре соединение,
     * через которое в дальнейшем класс {@link PrescriptionJdbcDAO} будет обращаться к БД
     */
    public PrescriptionJdbcDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Prescription> getAll() {
        ArrayList<Prescription> prescriptionList = new ArrayList<>();
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("select * from PRESCRIPTION")) {
            while (rs.next()) {
                prescriptionList.add(new Prescription(
                        rs.getLong("prescription_id"),
                        rs.getString("description"),
                        rs.getLong("patient_id"),
                        rs.getLong("doctor_id"),
                        rs.getDate("date_creat"),
                        rs.getLong("validity_period"),
                        PrescriptionPriorityType.valueOf(rs.getString("priority"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptionList;
    }

    @Override
    public Prescription getById(long id) {
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("select * from PRESCRIPTION where PRESCRIPTION_ID=" + id)) {
            if (rs.next())
                return new Prescription(
                        id,
                        rs.getString("description"),
                        rs.getLong("patient_id"),
                        rs.getLong("doctor_id"),
                        rs.getDate("date_creat"),
                        rs.getLong("validity_period"),
                        PrescriptionPriorityType.valueOf(rs.getString("priority")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Prescription prescription) {
        try (final Statement stm = connection.createStatement()) {
            String query = "INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY) " +
                    "VALUES ('" +
                    prescription.getDescription() + "','" +
                    prescription.getPatientId() + "','" +
                    prescription.getDoctorId() + "','" +
                    prescription.getDateCreat() + "','" +
                    prescription.getValidityPeriod() + "','" +
                    prescription.getPriority().toString() + "');";
            System.out.println(query);
            stm.executeUpdate(query);
            //получим ID последнего insert в БД чтобы Prescription был консистентен поcле вызова insert()
            try (ResultSet rs = stm.executeQuery("CALL IDENTITY()")) {
                if (rs.next()) {                            //if rs.next() returns false
                    prescription.setPrescriptionId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Prescription prescription) {
        try (Statement stm = connection.createStatement()) {
            String query = "UPDATE PRESCRIPTION SET " +
                    "PRESCRIPTION_ID='" + prescription.getPrescriptionId() +
                    "', DESCRIPTION ='" + prescription.getDescription() +
                    "', PATIENT_ID='" + prescription.getPatientId() +
                    "', DOCTOR_ID ='" + prescription.getDoctorId() +
                    "', DATE_CREAT ='" + prescription.getDateCreat() +
                    "', VALIDITY_PERIOD ='" + prescription.getValidityPeriod() +
                    "', PRIORITY ='" + prescription.getPriority().toString() +
                    "'  WHERE PRESCRIPTION_ID='" + prescription.getPrescriptionId() + "';";
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Prescription prescription) {
        try (Statement stm = connection.createStatement()) {
            String query = "DELETE FROM PRESCRIPTION WHERE PRESCRIPTION_ID='" + prescription.getPrescriptionId() + "';";
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}