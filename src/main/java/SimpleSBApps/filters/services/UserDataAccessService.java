package SimpleSBApps.filters.services;

import SimpleSBApps.filters.datastore.TempUserDataStore;
import SimpleSBApps.filters.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDataAccessService {

    private final TempUserDataStore tempUserDataSore;

    @Autowired
    public UserDataAccessService(TempUserDataStore tempUserDataSore) {
        this.tempUserDataSore = tempUserDataSore;
    }

    public List<User> getUsers() {
        return tempUserDataSore.getUsers();
    }
}
