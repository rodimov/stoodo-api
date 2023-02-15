package fr.stoodev.stoodo.user;

import org.springframework.data.domain.Page;

public interface UserService {
    User create(User user);
    UserDTO getOne(int userId);
    Page<UserDTO> getList(int page, int size);
}
