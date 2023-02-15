package fr.stoodev.stoodo.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public User create(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserDTO getOne(int userId) {
        Optional<User> user = this.userRepository.findById(userId);
        return user.map(value -> this.modelMapper.map(value, UserDTO.class)).orElse(null);
    }

    @Override
    public Page<UserDTO> getList(int page, int size) {
        PageRequest pr = PageRequest.of(page,size);
        Page<User> usersPage = this.userRepository.findAll(pr);

        return usersPage.map(user -> this.modelMapper.map(user, UserDTO.class));
    }
}
