package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO saveUser(UserDTO user);

    UserDTO getUserById(Long userId);

    boolean deleteUser(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);
}
