package app.DAO;

import java.util.List;

public interface IDAO <T, I> {

    boolean save (T entitty);
    T findById (I id);
    List<T> getAll();
    T update(T entity);
    boolean delete(I id);

}
