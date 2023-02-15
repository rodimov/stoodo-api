package fr.stoodev.stoodo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(final UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setId(null);
        return new ResponseEntity<>(this.userServiceImpl.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable("id") int userId) {
        return new ResponseEntity<>(this.userServiceImpl.getOne(userId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserDTO>> getList(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.userServiceImpl.getList(page, size), HttpStatus.OK);
    }
}
