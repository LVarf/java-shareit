package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static User mapperToUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        return user;
    }

    public static UserDTO mapperToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
