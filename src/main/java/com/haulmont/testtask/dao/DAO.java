package com.haulmont.testtask.dao;

import java.util.List;


public interface DAO<T> {
    /**
     * Возвращает список объектов соответствующих всем записям в базе данных
     */
    List<T> getAll();

    /**
     * Возвращает объект соответствующий записи с первичным ключом id
     */
    T getById(long id);

    /**
     * Создает новую запись
     */
    void insert(T object);

    /**
     * Сохраняет состояние объекта group в базе данных
     * При этом в объект записывается его id, под которым он состоит в БД
     */
    void update(T object);

    /**
     * Удаляет запись об объекте из базы данных
     */
    void delete(T object);


}