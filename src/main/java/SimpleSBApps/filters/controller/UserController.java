package SimpleSBApps.filters.controller;

import SimpleSBApps.filters.model.User;
import SimpleSBApps.filters.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Map;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(path = "/newUser")
    public void createNewUser(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String hashedPassword = encoder.encode(payload.get("password"));
            User user = new User(username);
            user.setHashedPassword(hashedPassword);
            userService.addUser(user);

        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalStateException("couldn't read user info");
        }
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{userId}/image/upload"
    )
    public void uploadImage(@PathVariable("userId")UUID userId, @RequestParam("file")MultipartFile file) {
        userService.uploadImage(userId, file);
    }

    @GetMapping(path = "/{userId}/image/download")
    public byte[] downloadUserImage(@PathVariable("userId")UUID userId) {
        return userService.retrieveImage(userId);
    }
}
