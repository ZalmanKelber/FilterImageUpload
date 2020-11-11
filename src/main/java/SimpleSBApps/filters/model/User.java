package SimpleSBApps.filters.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID userId;
    private String username;
    private ArrayList<String> userImageLinks;


    public User(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
        userImageLinks = new ArrayList<String>();
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getUserImageLinks() {
        return userImageLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, userImageLinks);
    }
}
