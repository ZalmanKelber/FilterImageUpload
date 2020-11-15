package SimpleSBApps.filters.datastore;

import SimpleSBApps.filters.config.PasswordConfig;
import SimpleSBApps.filters.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TempUserDataStore {

    private static final List<User> USERS = new ArrayList<User>();
    private static final PasswordEncoder encoder = new PasswordConfig().encoder();

    static {
        USERS.add(new User(null,
                "Qianlong",
                encoder.encode("dragon123"),
                true,
                true,
                true,
                true,
                UUID.fromString("b8718732-6eb9-4e3f-adb7-4bbdd0bb4a0a")));
        USERS.add(new User(null,
                "Yongzheng",
                encoder.encode("dragon123"),
                true,
                true,
                true,
                true,
                UUID.fromString("07c7fa7c-7b38-45ba-87e0-a988582f95bf")));
        USERS.add(new User(null,
                "Kangxi",
                encoder.encode("dragon123"),
                true,
                true,
                true,
                true,
                UUID.fromString("2c74e6b0-1dbc-45ec-947e-78d627820a22")));
    }

    public void addUser(User user) {
        USERS.add(user);
    }

    public List<User> getUsers() {
        return USERS;
    }
}
