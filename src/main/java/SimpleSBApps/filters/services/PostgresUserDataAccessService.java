package SimpleSBApps.filters.services;

import SimpleSBApps.filters.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository("postgres")
public class PostgresUserDataAccessService implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresUserDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addUser(User user) {
        final String sql = "INSERT INTO imageuser (id, username, imageIndex) VALUES (?, ?, ?)";
        UUID id = user.getUserId();
        String username = user.getUsername();
        int imageIndex = user.getProfileImageIndex();
        jdbcTemplate.update(sql, new Object[]{id, username, imageIndex});
        user.getUserImageLinks().stream().forEach(filename -> addImageLink(id, filename));
    }

    @Override
    public List<User> getAllUsers() {
        final String sql1 = "SELECT * FROM imageuser";
        HashMap<UUID, Integer> hm = new HashMap<UUID, Integer>();
        List<User> allUsers = jdbcTemplate.query(sql1, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String username = resultSet.getString("username");
            int imageIndex = Integer.parseInt(resultSet.getString("imageIndex"));
            User user = new User(id, username);
            user.setNewProfileImageIndex(imageIndex);
            hm.put(id, i);
            return user;
        });
        final String sql2 = "SELECT (userId, filename) FROM image ORDER BY id";
        jdbcTemplate.query(sql2, (resultSet, i) -> {
            UUID userId = UUID.fromString(resultSet.getString("userId"));
            String filename = resultSet.getString("filename");
            User user = allUsers.get(hm.get(userId));
            user.addUserImageLink(filename);
            return null;
        });

        return allUsers;

    }

    @Override
    public User findUserOrThrowError(UUID userId) {
        final String sql1 = "SELECT * FROM imageuser WHERE id = ?";
        List<User> foundUsers = jdbcTemplate.query(sql1, new Object[]{userId}, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String username = resultSet.getString("username");
            int imageIndex = Integer.parseInt(resultSet.getString("imageIndex"));
            User user = new User(id, username);
            user.setNewProfileImageIndex(imageIndex);
            return user;
        });
        if (foundUsers.size() == 0) {
            throw new IllegalStateException("user not found");
        } else {
            User user = foundUsers.get(0);
            final String sql2 = "SELECT filename FROM image WHERE userId = ? ORDER BY id";
            jdbcTemplate.query(sql2, new Object[]{userId}, (resultSet, i) -> {
                String filename = resultSet.getString("filename");
                user.addUserImageLink(filename);
                return null;
            });
            return user;
        }

    }

    @Override
    public void deleteUserById(UUID userId) {
        final String sql1 = "DELETE FROM imageuser WHERE id = ?";
        jdbcTemplate.update(sql1, new Object[]{userId});
        final String sql2 = "DELETE FROM image WHERE userId = ?";
        jdbcTemplate.update(sql2, new Object[]{userId});
    }

    @Override
    public void addImageLink(UUID userId, String filename) {
        final String sql = "INSERT INTO image (userId, filename) VALUES (?, ?)";
        jdbcTemplate.update(sql, new Object[]{userId, filename});
    }

    @Override
    public String getProfileImageFilename(UUID userId) {
        User user = findUserOrThrowError(userId);
        List<String> images = user.getUserImageLinks();
        if (images.size() == 0) {
            throw new IllegalStateException("user has no images available");
        }
        int index = user.getProfileImageIndex();
        index = index < 0 ? images.size() - 1 : index;
        return user.getUserImageLinks().get(index);
    }

    @Override
    public List<String> getAllImageFilenames(UUID userId) {
        final String sql = "SELECT filename FROM image WHERE userId = ? ORDER BY id";
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, i) -> {
            return resultSet.getString("filename");
        });
    }

    @Override
    public void setIndex(UUID userId, int index) {
        final String sql = "UPDATE imageuser SET imageIndex = ? WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{index, userId});
    }
}
