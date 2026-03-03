package ma.projet.dao;


import java.util.List;

/**
 *
 * @author X1 YOGA
 */
public interface IDao<T> {

    boolean create(T o);

    boolean delete(T o);

    boolean update(T o);

    T findById(int id);

    List<T> findAll();

}

