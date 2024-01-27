package kg.rakhim.classes.database;

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Storage {
    private List<User> users = new ArrayList<>();
    private List<MeterReading> meterReadings = new ArrayList<>();
    private List<Audit> audits = new ArrayList<>();


    public Storage() {
        User admin = new User("admin", "adminpass", UserRole.ADMIN);
        users.add(admin);
        meterReadings.add(new MeterReading("горячая вода"));
        meterReadings.add(new MeterReading("холодная вода"));
        meterReadings.add(new MeterReading("отопление"));
    }


    public User getUser(String username) {
        for (User u : this.getUsers()) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }
}
