package kg.rakhim.classes.dao.interfaces;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <T> Класс
 * @param <U> Идентификатор класса
 */
public interface BaseDAO <T, U>{
    Optional<T> get(int id);
    List<T> getAll();
    void save(T t);
}
