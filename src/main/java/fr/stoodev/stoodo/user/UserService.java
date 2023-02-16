package fr.stoodev.stoodo.user;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    UserInfoDTO create(UserCreationDTO userCreationDTO);
    Optional<UserInfoDTO> getOne(int userId);
    Page<UserInfoDTO> getList(int page, int size);
}
