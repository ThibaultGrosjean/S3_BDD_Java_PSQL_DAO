package dao;//package dao;

import dao.exception.DaoException;
import model.Entity;

import java.util.Collection;

public interface Dao {
    Collection<Entity> findAll() throws DaoException;
    Entity findById(int id) throws DaoException;
    void create(Entity entity) throws DaoException;
    void update(Entity entity) throws DaoException;
    void delete(Entity entity) throws DaoException;
}