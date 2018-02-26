package nl.oscar.kwetter.dao;

import java.util.Collection;

/**
 * DAO defines the standard operations that should be available for every specialized DAO.
 *
 * @param <I> The class that is used for identifying the object in the database.
 * @param <T> The class that is managed by this DAO.
 * @author Oscar de Leeuw
 */
public interface Dao<I, T> {

    /**
     * Creates a record in the database for the given object.
     *
     * @param object The object that should be saved to the database.
     */
    void create(T object);

    /**
     * Edits a record of an object in the database.
     *
     * @param object The object that should be updated in the database.
     */
    void edit(T object);

    /**
     * Finds an object in the database with the given i.
     *
     * @param i The id of the object.
     * @return The object with the given i from the database.
     */
    T find(I i);

    /**
     * Finds all the objects of this type in the database.
     *
     * @return A List with all the objects.
     */
    Collection<T> findAll();

    /**
     * Removes the given object from the database.
     *
     * @param object The object that should be removed from the database.
     */
    void remove(T object);

}
