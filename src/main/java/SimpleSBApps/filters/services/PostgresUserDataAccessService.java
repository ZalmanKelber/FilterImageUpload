package SimpleSBApps.filters.services;

import SimpleSBApps.filters.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres")
public class PostgresUserDataAccessService implements UserDao {

    @Override
    public void addUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User findUserOrThrowError(UUID userId) {
        return null;
    }

    @Override
    public void deleteUserById(UUID userId) {

    }

    @Override
    public void addImageLink(UUID userId, String filename) {

    }

    @Override
    public String getProfileImageFilename(UUID userId) {
        return null;
    }

    @Override
    public List<String> getAllImageFilenames(UUID userId) {
        return null;
    }

    @Override
    public void setIndex(UUID userId, int index) {

    }
}
