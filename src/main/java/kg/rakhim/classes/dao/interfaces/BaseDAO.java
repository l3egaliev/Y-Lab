package kg.rakhim.classes.dao.interfaces;

import java.util.List;

/**
 *
 * @param <T> Класс
 * @param <U> Идентификатор класса
 */
public interface BaseDAO <T, U>{
    T get(int id);
    List<T> getAll();
    void save(T t);
}
