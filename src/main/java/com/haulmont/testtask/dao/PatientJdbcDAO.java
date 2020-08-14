package com.haulmont.testtask.dao;

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

//    @Override
////    public ArrayList<Patient> getAll() {
////        ArrayList<Patient> patientsList = new ArrayList<>();
////        try (Statement stm = connection.createStatement();
////             ResultSet rs = stm.executeQuery("select * from PATIENT")) {
////            while (rs.next()) {
////                String sqlLastName = rs.getString("last_name");
////                String sqlFirstName = rs.getString("first_name");
////                patientsList.add(new Patient(
////                        rs.getLong("patient_id"),
////                        rs.getString("last_name"),
////                        rs.getString("first_name"),
////                        rs.getString("patronymic"),
////                        rs.getString("phone_number")));
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return patientsList;
////    }


    @Override
    public ArrayList<Patient> getAll() {
        ArrayList<Patient> patientsList = new ArrayList<>();
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(
                     "SELECT PATIENT_ID,\n" +
                             "       LAST_NAME,\n" +
                             "       FIRST_NAME,\n" +
                             "       PATRONYMIC,\n" +
                             "       PHONE_NUMBER,\n" +
                             "       PATIENT_ID not in (\n" +
                             "           SELECT DISTINCT PATIENT_ID\n" +
                             "           from PRESCRIPTION)\n" +
                             "from PATIENT")) {
            while (rs.next()) {
                patientsList.add(new Patient(
                        rs.getLong("patient_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("patronymic"),
                        rs.getString("phone_number"),
                        rs.getBoolean(6)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patientsList;
    }

    @Override
    public Patient getById(long id) {
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("" +
                     "SELECT PATIENT_ID,\n" +
                     "       LAST_NAME,\n" +
                     "       FIRST_NAME,\n" +
                     "       PATRONYMIC,\n" +
                     "       PHONE_NUMBER,\n" +
                     "       PATIENT_ID not in (\n" +
                     "           SELECT DISTINCT PATIENT_ID\n" +
                     "           from PRESCRIPTION)\n" +
                     "from PATIENT where PATIENT_ID=" + id)) {
            if (rs.next())
                return new Patient(
                        id,
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("patronymic"),
                        rs.getString("phone_number"),
                        rs.getBoolean(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Patient patient) {
        try (final Statement stm = connection.createStatement()) {
            String query = "INSERT INTO PATIENT (LAST_NAME, FIRST_NAME, PATRONYMIC, PHONE_NUMBER) " +
                    "VALUES ('" +
                    patient.getLastName() + "','" +
                    patient.getFirstName() + "','" +
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
                    "LAST_NAME='" + patient.getLastName() +
                    "', FIRST_NAME ='" + patient.getFirstName() +
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