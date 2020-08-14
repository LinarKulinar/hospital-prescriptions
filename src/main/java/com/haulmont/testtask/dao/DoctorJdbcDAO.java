package com.haulmont.testtask.dao;


import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.PrescriptionPriorityType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
             ResultSet rs = stm.executeQuery("" +
                     "SELECT DOCTOR_ID,\n" +
                     "       LAST_NAME,\n" +
                     "       FIRST_NAME,\n" +
                     "       PATRONYMIC,\n" +
                     "       SPECIALIZATION,\n" +
                     "       DOCTOR_ID not in (\n" +
                     "           SELECT DISTINCT DOCTOR_ID\n" +
                     "           from PRESCRIPTION)\n" +
                     "from DOCTOR")) {
            while (rs.next()) {
                doctorsList.add(new Doctor(
                        rs.getLong("doctor_id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("patronymic"),
                        rs.getString("specialization"),
                        rs.getBoolean(6)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorsList;
    }

    @Override
    public Doctor getById(long id) {
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("" +
                     "SELECT DOCTOR_ID,\n" +
                     "       LAST_NAME,\n" +
                     "       FIRST_NAME,\n" +
                     "       PATRONYMIC,\n" +
                     "       SPECIALIZATION,\n" +
                     "       DOCTOR_ID not in (\n" +
                     "           SELECT DISTINCT DOCTOR_ID\n" +
                     "           from PRESCRIPTION)\n" +
                     "from DOCTOR where DOCTOR_ID=" + id)) {
            if (rs.next())
                return new Doctor(
                        id,
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("patronymic"),
                        rs.getString("specialization"),
                        rs.getBoolean(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Doctor doctor) {
        try (Statement stm = connection.createStatement()) {
            String query = "INSERT INTO DOCTOR (LAST_NAME, FIRST_NAME, PATRONYMIC, SPECIALIZATION) " +
                    "VALUES ('" +
                    doctor.getLastName() + "','" +
                    doctor.getFirstName() + "','" +
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
                    "LAST_NAME='" + doctor.getLastName() +
                    "', FIRST_NAME ='" + doctor.getFirstName() +
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

    public Map<PrescriptionPriorityType, Long> getStatistic(Doctor doctor) {
        try (Statement stm = connection.createStatement();
             ResultSet rs = stm.executeQuery("" +
                     "select PRESCRIPTION.PRIORITY, count(PRESCRIPTION.PRIORITY)\n" +
                     "from DOCTOR, PRESCRIPTION\n" +
                     "where DOCTOR.DOCTOR_ID=" + doctor.getDoctorId() + " and DOCTOR.DOCTOR_ID=PRESCRIPTION.DOCTOR_ID\n" +
                     "group by PRESCRIPTION.PRIORITY")) {
            Map<PrescriptionPriorityType, Long> map = new HashMap<>();
            for (PrescriptionPriorityType type : PrescriptionPriorityType.values()) { //Создаем пустую мапу
                map.put(type, 0L);
            }
            while (rs.next()) { // идем построчно и инкрементируем на величину второго столбца
                PrescriptionPriorityType currentType = PrescriptionPriorityType.valueOf(rs.getString("priority"));
                map.put(currentType, map.get(currentType) + rs.getLong(2)); //инкрементируем
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}