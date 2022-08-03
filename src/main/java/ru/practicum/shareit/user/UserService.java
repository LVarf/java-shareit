package ru.practicum.shareit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    User saveUser(User user);

    UserDTO getUserById(long userId);

    boolean deleteUser(long userId);

    UserDTO updateUser(long userId, String params) throws JsonProcessingException;
}
