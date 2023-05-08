package fr.stoodev.stoodo.user;

import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserInfoDTO create(UserCreationDTO userCreationDTO);
    Optional<UserInfoDTO> getOne(UUID userId);
    Page<UserInfoDTO> getList(int page, int size);
}
