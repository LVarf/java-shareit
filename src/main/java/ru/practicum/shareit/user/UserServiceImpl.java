package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private long newUserId = 1;

    //region getAllUsers
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapperToUserDTO)
                .collect(Collectors.toList());
    }
    //endregion

    //region SaveUser
    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return UserMapper.mapperToUserDTO(userRepository.save(UserMapper.mapperToUser(userDTO)));
    }
    //endregion

    //region getUserById
    @Override
    public UserDTO getUserById(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent())
            return UserMapper.mapperToUserDTO(userRepository.findById(userId).get());
        else throw new NotFoundObjectException("The user is not found");
    }
    //endregion

    //region deleteUser
    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    //endregion

    //region updateUser
    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = Optional.of(userRepository.findById(userId))
                .orElseThrow(() -> new IllegalArgumentException("There is no the user"))
                .get();

        if(userDTO.getEmail() != null){
            user.setEmail(userDTO.getEmail());}
        if (userDTO.getName() != null)
            user.setName(userDTO.getName());

        return UserMapper.mapperToUserDTO(userRepository.save(user));
    }
    //endregion

    private long getNewUserId(){
        return newUserId++;
    }


}
