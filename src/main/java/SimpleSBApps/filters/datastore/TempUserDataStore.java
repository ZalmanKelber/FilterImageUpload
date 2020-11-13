package SimpleSBApps.filters.datastore;

import SimpleSBApps.filters.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TempUserDataStore {

    private static final List<User> USERS = new ArrayList<User>();

    static {
        USERS.add(new User(UUID.fromString("b8718732-6eb9-4e3f-adb7-4bbdd0bb4a0a"), "Qianlong"));
        USERS.add(new User(UUID.fromString("07c7fa7c-7b38-45ba-87e0-a988582f95bf"), "Yongzheng"));
        USERS.add(new User(UUID.fromString("2c74e6b0-1dbc-45ec-947e-78d627820a22"), "Kangxi"));
    }

    public void addUser(User user) {
        USERS.add(user);
    }

    public List<User> getUsers() {
        return USERS;
    }
}
