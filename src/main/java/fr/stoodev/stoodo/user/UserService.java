package fr.stoodev.stoodo.user;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<UserDTO> getOne(int userId);
    Page<UserDTO> getList(int page, int size);
}
