package kg.rakhim.classes.repository;

import kg.rakhim.classes.models.User;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface BaseRepository <T>{
    Optional<T> findById(int id);
    List<T> findAll();
    void save(T e);
}
