package SimpleSBApps.filters.services;

import SimpleSBApps.filters.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    void addUser(User user);

    List<User> getAllUsers();

    User findUserOrThrowError(UUID userId);

    void deleteUserById(UUID userId);

    void addImageLink(UUID userId, String filename);

    String getProfileImageFilename(UUID userId);

    List<String> getAllImageFilenames(UUID userId);

    void setIndex(UUID userId, int index);

}
