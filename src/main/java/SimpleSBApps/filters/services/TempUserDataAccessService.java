package SimpleSBApps.filters.services;

import SimpleSBApps.filters.datastore.TempUserDataStore;
import SimpleSBApps.filters.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("fakeDao")
public class TempUserDataAccessService implements UserDao {

    private final TempUserDataStore tempUserDataStore;

    @Autowired
    public TempUserDataAccessService(TempUserDataStore tempUserDataStore) {
        this.tempUserDataStore = tempUserDataStore;
    }

    @Override
    public void addUser(User user) {
        tempUserDataStore.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return tempUserDataStore.getUsers();
    }

    @Override
    public User findUserOrThrowError(UUID userId) {
        return getAllUsers().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("couldn't find user"));
    }

    @Override
    public void deleteUserById(UUID userId) {
        getAllUsers().removeIf(user -> user.getUserId().equals(userId));
    }

    @Override
    public void addImageLink(UUID userId, String filename) {
        User user = findUserOrThrowError(userId);
        user.addUserImageLink(filename);
    }

    @Override
    public String getProfileImageFilename(UUID userId) {
        User user = findUserOrThrowError(userId);
        int ind = user.getProfileImageIndex();
        return user.getUserImageLinks().get(ind);
    }

    @Override
    public List<String> getAllImageFilenames(UUID userId) {
        User user = findUserOrThrowError(userId);
        return user.getUserImageLinks();
    }

    @Override
    public void setIndex(UUID userId, int index) {
        User user = findUserOrThrowError(userId);
        user.setNewProfileImageIndex(index);
    }
}
