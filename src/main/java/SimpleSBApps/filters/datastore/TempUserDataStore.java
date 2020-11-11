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
        USERS.add(new User(UUID.randomUUID(), "Qianlong"));
        USERS.add(new User(UUID.randomUUID(), "Yongzheng"));
        USERS.add(new User(UUID.randomUUID(), "Kangxi"));
    }

    public List<User> getUsers() {
        return USERS;
    }
}
