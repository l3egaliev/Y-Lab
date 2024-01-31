package kg.rakhim.classes.out;

/**
 * Класс предоставляет статические методы для вывода данных в консоль.
 */
public class ConsoleOut {

    /**
     * Метод используется для вывода данных с переводом строки в консоль.
     *
     * @param data Объект, который нужно вывести в консоль.
     */
    public static void printLine(Object data) {
        System.out.println(data);
    }

    /**
     * Метод используется для вывода данных без перевода строки в консоль.
     *
     * @param data Объект, который нужно вывести в консоль.
     */
    public static void print(Object data) {
        System.out.print(data);
    }
}