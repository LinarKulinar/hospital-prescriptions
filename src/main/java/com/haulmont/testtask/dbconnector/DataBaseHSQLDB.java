package com.haulmont.testtask.dbconnector;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dao.PatientJdbcDAO;
import com.haulmont.testtask.dao.PrescriptionJdbcDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Данный класс необходим для взаимодействия с БД HSQLDB
 */
public class DataBaseHSQLDB extends DataBaseFactory {
    static Connection connection = null;


    /**
     * Возвращает экзампляр коннектора к БД.
     * Если до этого соединение отсутствовало, то создаст его
     */
    public DataBaseHSQLDB getInstance() {
        if (connection == null) {
            try {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                System.out.println("HSQLDB JDBCDriver Loaded");
                connection = DriverManager.getConnection(
                        "jdbc:hsqldb:file:/db/hospitaldb", "SA", "");
                System.out.println("HSQLDB Connection Created");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Откключает базу данных и закрывает открытое соединение
     */
    public synchronized void shutdown() {
        try {
            Statement stmnt = connection.createStatement();
            stmnt.execute("SHUTDOWN");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Возвращаем DAO класс сущности {@link com.haulmont.testtask.model.Patient},
     * который, используя JDBC, реализует методы необходимые для CRUD
     */
    @Override
    public PatientJdbcDAO getPatientDao() {
        return new PatientJdbcDAO(connection);
    }

    /**
     * Возвращаем DAO класс сущности {@link com.haulmont.testtask.model.Doctor},
     * который, используя JDBC, реализует методы необходимые для CRUD
     */
    @Override
    public DoctorJdbcDAO getDoctorDao() {
        return new DoctorJdbcDAO(connection);
    }

    /**
     * Возвращаем DAO класс сущности {@link com.haulmont.testtask.model.Prescription},
     * который, используя JDBC, реализует методы необходимые для CRUD
     */
    @Override
    public PrescriptionJdbcDAO getPrescriptionDao() {
        return new PrescriptionJdbcDAO(connection);
    }
}