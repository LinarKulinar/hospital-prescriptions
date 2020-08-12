package com.haulmont.testtask.dao;

import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PatientJdbcDAO implements DAO<Patient> {
    private Connection connection;

    /**
     * При вызове конструктора необходимо указать в параметре соединение,
     * через которое в дальнейшем класс {@link PatientJdbcDAO} будет обращаться к БД
     */
    public PatientJdbcDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Patient> getAll() {
        ArrayList<Patient> patientsList = new ArrayList<>();
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("select * from PATIENT")) {
            while (rs.next()) {
                patientsList.add(new Patient(
                        rs.getLong("patient_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("patronymic"),
                        rs.getString("phone_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientsList;
    }

    @Override
    public Patient getById(long id) {
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("select * from PATIENT where PATIENT_ID=" + id)) {
            if (rs.next())
                return new Patient(
                        id,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("patronymic"),
                        rs.getString("phone_number"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Patient patient) {
        try (final Statement stm = connection.createStatement()) {
            String query = "INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER) " +
                    "VALUES ('" +
                    patient.getFirstName() + "','" +
                    patient.getLastName() + "','" +
                    patient.getPatronymic() + "','" +
                    patient.getPhoneNumber() + "');";
            System.out.println(query);
            stm.executeUpdate(query);
            //получим ID последнего insert в БД чтобы Patient был консистентен поcле вызова insert()
            try (ResultSet rs = stm.executeQuery("CALL IDENTITY()")) {
                if (rs.next()) {                            //if rs.next() returns false
                    patient.setPatientId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Patient patient) {
        try (Statement stm = connection.createStatement()) {
            String query = "UPDATE PATIENT SET " +
                    "FIRST_NAME='" + patient.getFirstName() +
                    "', LAST_NAME ='" + patient.getLastName() +
                    "', PATRONYMIC='" + patient.getPatronymic() +
                    "', PHONE_NUMBER ='" + patient.getPhoneNumber() +
                    "'  WHERE PATIENT_ID='" + patient.getPatientId() + "';";
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Patient patient) {
        try (Statement stm = connection.createStatement()) {
            String query = "DELETE FROM PATIENT WHERE PATIENT_ID='" + patient.getPatientId() + "';";
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}