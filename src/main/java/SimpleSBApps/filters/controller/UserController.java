package SimpleSBApps.filters.controller;

import SimpleSBApps.filters.model.User;
import SimpleSBApps.filters.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{userId}/image/upload"
    )
    public void uploadImage(@PathVariable("userId")UUID userId, @RequestParam("file")MultipartFile file) {
        userService.uploadImage(userId, file);
    }
}
