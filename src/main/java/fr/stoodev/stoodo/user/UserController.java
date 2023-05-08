package fr.stoodev.stoodo.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "The User API. Contains all the operations that can be performed on a user.")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create user", description = "Create user")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserInfoDTO> create(@RequestBody UserCreationDTO user) {
        return new ResponseEntity<>(this.userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get user", description = "Return user by id")
    public ResponseEntity<UserInfoDTO> getOne(@PathVariable("id") UUID userId) {
        Optional<UserInfoDTO> user = this.userService.getOne(userId);

        return user.map(userInfoDTO -> new ResponseEntity<>(userInfoDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/list")
    @Operation(summary = "Get users list", description = "Return users list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<UserInfoDTO>> getList(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(this.userService.getList(page, size), HttpStatus.OK);
    }
}
