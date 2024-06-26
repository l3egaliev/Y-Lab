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

public class MeterType {
    /**
     * Строковое представление типа счетчика.
     */
    private String type;
    private Integer id;

    public MeterType(String type){
        this.type = type;
    }

    public MeterType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
