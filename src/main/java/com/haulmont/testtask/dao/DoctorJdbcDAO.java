package com.haulmont.testtask.dao;


import com.haulmont.testtask.dbconnector.DataBaseFactory;
import com.haulmont.testtask.dbconnector.DataBaseType;
import com.haulmont.testtask.model.Doctor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DoctorJdbcDAO implements DAO<Doctor> {
    private Connection connection;

    /**
     * При вызове конструктора необходимо указать в параметре соединение,
     * через которое в дальнейшем класс {@link DoctorJdbcDAO} будет обращаться к БД
     */
    public DoctorJdbcDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Doctor> getAll() {
        ArrayList<Doctor> doctorsList = new ArrayList<>();
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("select * from DOCTOR")) {
            while (rs.next()) {
                doctorsList.add(new Doctor(
                        rs.getLong("doctor_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("patronymic"),
                        rs.getString("specialization")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorsList;
    }

    @Override
    public Doctor getById(long id) {
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("select * from DOCTOR where DOCTOR_ID=" + id)) {
            if (rs.next())
                return new Doctor(
                        id,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("patronymic"),
                        rs.getString("specialization"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Doctor doctor) {
        try (Statement stm = connection.createStatement()) {
            String query = "INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION) " +
                    "VALUES ('" +
                    doctor.getFirstName() + "','" +
                    doctor.getLastName() + "','" +
                    doctor.getPatronymic() + "','" +
                    doctor.getSpecialization() + "');";
            System.out.println(query);
            stm.executeUpdate(query);
            //получим ID последнего insert в БД чтобы Prescription был консистентен поcле вызова insert()
            try (ResultSet rs = stm.executeQuery("CALL IDENTITY()")) {
                if (rs.next()) {                            //if rs.next() returns false
                    doctor.setDoctorId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Doctor doctor) {
        try (Statement stm = connection.createStatement()) {
            String query = "UPDATE DOCTOR SET " +
                    "FIRST_NAME='" + doctor.getFirstName() +
                    "', LAST_NAME ='" + doctor.getLastName() +
                    "', PATRONYMIC='" + doctor.getPatronymic() +
                    "', SPECIALIZATION ='" + doctor.getSpecialization() +
                    "'  WHERE DOCTOR_ID='" + doctor.getDoctorId() + "';";
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Doctor doctor) {
        try (Statement stm = connection.createStatement()) {
            String query = "DELETE FROM DOCTOR WHERE DOCTOR_ID='" + doctor.getDoctorId() + "';";
            System.out.println(query);
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}