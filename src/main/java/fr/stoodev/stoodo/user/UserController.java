package fr.stoodev.stoodo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserInfoDTO> create(@RequestBody UserCreationDTO user) {
        return new ResponseEntity<>(this.userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserInfoDTO> getOne(@PathVariable("id") int userId) {
        Optional<UserInfoDTO> user = this.userService.getOne(userId);

        return user.map(userInfoDTO -> new ResponseEntity<>(userInfoDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserInfoDTO>> getList(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.userService.getList(page, size), HttpStatus.OK);
    }
}
