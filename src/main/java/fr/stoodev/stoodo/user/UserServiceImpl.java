package fr.stoodev.stoodo.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserInfoDTO create(UserCreationDTO userCreationDTO) {
        User user = this.modelMapper.map(userCreationDTO, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);

        user = this.userRepository.save(user);
        return this.modelMapper.map(user, UserInfoDTO.class);
    }

    @Override
    public Optional<UserInfoDTO> getOne(UUID userId) {
        Optional<User> user = this.userRepository.findById(userId);
        return user.map(value -> this.modelMapper.map(value, UserInfoDTO.class));
    }

    @Override
    public Page<UserInfoDTO> getList(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<User> usersPage = this.userRepository.findAll(pr);

        return usersPage.map(user -> this.modelMapper.map(user, UserInfoDTO.class));
    }
}
