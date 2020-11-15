package SimpleSBApps.filters.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {

    private final UUID userId;
    private Set<? extends GrantedAuthority> grantedAuthorities;
    private final String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private ArrayList<String> userImageLinks;
    private int profileImageIndex;


    public User(Set<? extends GrantedAuthority> grantedAuthorities,
                           String username,
                           String password,
                           boolean accountNonExpired,
                           boolean accountNonLocked,
                           boolean credentialsNonExpired,
                           boolean enabled,
                           UUID userId) {
        this.grantedAuthorities = grantedAuthorities;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.userId = userId;
    }

    public User(Set<? extends GrantedAuthority> grantedAuthorities,
                String username,
                String password,
                boolean accountNonExpired,
                boolean accountNonLocked,
                boolean credentialsNonExpired,
                boolean enabled) {
        this (grantedAuthorities,
                username,
                password,
                accountNonExpired,
                accountNonLocked,
                credentialsNonExpired,
                enabled,
                UUID.randomUUID());

    }


    public UUID getUserId() {
        return userId;
    }

    public int getProfileImageIndex() { return profileImageIndex; }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) { this.password = password; }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
