package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import java.time.LocalDateTime;
import java.util.Scanner;

import static kg.rakhim.classes.in.MeterReadingService.commandList;


public class AdminActions {
    private final Storage storage;
    private final Scanner scanner;


    public AdminActions(Storage storage, Scanner scanner){
        this.storage = storage;
        this.scanner = scanner;
    }


    public void viewActualReadingsOfUsers(String username) {
        System.out.println("Актуальные показания всех пользователей: ");
        for (MeterReading m : storage.getMeterReadings()){
            // не показываем показания user'а созданного системой
            if(!m.getUser().getUsername().equals("default")) {
                if (m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    System.out.println("\t- " + m);
                }
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр актуальных показаний всех пользователей", LocalDateTime.now()));
        commandList(username);
    }


    public void viewReadingsHistoryForMonth(int month, String username) {
        System.out.println("Все показания пользователей за "+month+" - месяц");
        for(MeterReading meterReading : storage.getMeterReadings()){
            // не показываем показания user'а созданного системой
            if (!meterReading.getUser().getUsername().equals("default")) {
                if (meterReading.getDate().getMonthValue() == month) {
                    System.out.println("\t- " + meterReading);
                }
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр историю показаний пользователя за конкретный месяц", LocalDateTime.now()));
        commandList(username);
    }



    public void viewReadingsHistoryOfUser(String username, String currentUser){
        System.out.println("Все показания пользователя "+username);
        for (MeterReading m : storage.getMeterReadings()){
            if (m.getUser().getUsername().equals(username))
                System.out.println("\t~ "+m);
        }
        storage.getAudits().add(new Audit(username, "Просмотр истории всех показаний конкретного пользователя",
                LocalDateTime.now()));
        commandList(currentUser);
    }

    public void setNewType(String username){
        if (username.equals("admin")){
            System.out.println("Название нового типа счетчика: ");
            String newType = scanner.next();
            String firstLetter = String.valueOf(newType.charAt(0));
            if (!(firstLetter.equalsIgnoreCase("г") || firstLetter.equalsIgnoreCase("о")
                    || firstLetter.equalsIgnoreCase("х"))){
                storage.getMeterReadings().add(new MeterReading(newType));
                System.out.println("Новый тип показания успешно добавлен");
                commandList(username);
            }
        }

        System.out.println("Не удалось добавить новый тип");
        commandList(username);
    }
}
