/**
 * Класс MeterType представляет собой модель типа счетчика.
 * Этот класс используется для представления различных типов счетчиков.
 * Он содержит основную информацию о типе счетчика и предоставляет метод
 * toString() для удобного получения строкового представления объекта.
 *
 * <p>Этот класс использует библиотеку Lombok для автоматической генерации
 * геттеров, сеттеров и конструктора с параметрами.
 *
 * @author Рахим Нуралиев
 * @version 1.0
 */
package kg.rakhim.classes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MeterType {
    /**
     * Строковое представление типа счетчика.
     */
    String type;

    /**
     * Переопределенный метод toString(), возвращающий строковое представление
     * объекта MeterType. В данном случае, возвращается строка, представляющая
     * тип счетчика.
     *
     * @return строковое представление типа счетчика
     */
    @Override
    public String toString() {
        return type;
    }
}
