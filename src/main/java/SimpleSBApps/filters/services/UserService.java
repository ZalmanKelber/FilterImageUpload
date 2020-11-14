package SimpleSBApps.filters.services;

import SimpleSBApps.filters.bucket.BucketName;
import SimpleSBApps.filters.filestore.FileStore;
import SimpleSBApps.filters.model.User;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private final UserDao userDao;
    private final FileStore fileStore;

    @Autowired
    public UserService(@Qualifier("postgres") UserDao userDao, FileStore fileStore) {
        this.userDao = userDao;
        this.fileStore = fileStore;
    }

    public List<User> getUsers() {
        return userDao.getAllUsers();
    }

    public User findUserOrThrowError(UUID userId) {
        return getUsers().stream().filter(user -> {
            return user.getUserId().equals(userId);
        }).findFirst().orElseThrow(() -> new IllegalStateException("user not found"));
    }

    public byte[] retrieveImage(UUID userId) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userId);
        String filename = userDao.getProfileImageFilename(userId);
        return fileStore.downloadImage(path, filename);
    }

    public void uploadImage(UUID userId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("file not found");
        }

        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("file type not supported");
        }

        User u = findUserOrThrowError(userId);

        Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userId);
        String originalFilename = file.getName();
        if (originalFilename.length() > 50) {
            originalFilename = originalFilename.substring(0,50);
        }
        String filename = String.format("%s-%s", originalFilename, UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            userDao.addImageLink(userId, filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        //2. check if file is image
        //3. check if user is in database
        //4. grab metadata if any
        //5. send file to s3 and update database with link
    }
}
