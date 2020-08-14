package com.haulmont.testtask.dbconnector;

import com.haulmont.testtask.dao.DoctorJdbcDAO;
import com.haulmont.testtask.dao.PatientJdbcDAO;
import com.haulmont.testtask.dao.PrescriptionJdbcDAO;

/**
 * Данный класс описывает фабрику для подключения к различным БД.
 * Мы можем обращаться к методам абстрактного интерфейса скрывая конкретную реализацию БД.
 */
public abstract class DataBaseFactory {

    /**
     * В данном конструкторе мы возвращаем Connector БД соответствующей типу {@link DataBaseType}
     *
     * @param type - тип БД, Connector которой будет создан
     * @return
     */
    public static DataBaseFactory getFactory(DataBaseType type) {
        switch (type) {
            case HSQLDB:
                return new DataBaseHSQLDB().getInstance();
            default:
                throw new RuntimeException("Attempting to connect to a database " + type.toString() +
                        " for which the DataBaseFactory class is not implemented");
        }

    }


    /**
     * Откключает базу данных и закрывает открытое соединение
     */
    public abstract void shutdown();

    /**
     * Возвращаем DAO класс сущности {@link com.haulmont.testtask.model.Patient},
     * который реализует методы необходимые для CRUD
     */
    public abstract PatientJdbcDAO getPatientDao();

    /**
     * Возвращаем DAO класс сущности {@link com.haulmont.testtask.model.Doctor},
     * который реализует методы необходимые для CRUD
     */
    public abstract DoctorJdbcDAO getDoctorDao();

    /**
     * Возвращаем DAO класс сущности {@link com.haulmont.testtask.model.Prescription},
     * который реализует методы необходимые для CRUD
     */
    public abstract PrescriptionJdbcDAO getPrescriptionDao();


}
