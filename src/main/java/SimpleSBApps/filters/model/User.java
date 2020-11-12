package SimpleSBApps.filters.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID userId;
    private String username;
    private ArrayList<String> userImageLinks;
    private int profileImageIndex;


    public User(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
        userImageLinks = new ArrayList<String>();
        profileImageIndex = -1;
    }

    public UUID getUserId() {
        return userId;
    }

    public int getProfileImageIndex() { return profileImageIndex; }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getUserImageLinks() {
        return userImageLinks;
    }

    public void addUserImageLink(String userImageLink) {
        userImageLinks.add(userImageLink);
    }

    public void deleteUserImageLink(String userImageLink) {
        userImageLinks.stream().filter(s -> !s.equals(userImageLink));
        int n = userImageLinks.size();
        profileImageIndex = profileImageIndex >= n ? n - 1 : profileImageIndex;
    }

    public int setNewProfileImageIndex(int i) {
        if (i >= userImageLinks.size()) {
            return 0;
        }
        profileImageIndex = i;
        return 1;
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
