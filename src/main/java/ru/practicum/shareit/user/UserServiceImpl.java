package ru.practicum.shareit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private long newUserId = 1;

    //region getAllUsers
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .map(UserMapper::mapperToUserDTO)
                .collect(Collectors.toList());
    }
    //endregion

    //region SaveUser
    @Override
    public UserDTO saveUser(UserDTO user) {
        boolean containEmail = userRepository.getAllUsers()
                .stream()
                .map(User::getEmail)
                .anyMatch(email -> email.equals(user.getEmail()));
        if(containEmail)
            throw new IllegalArgumentException("There is a user with such an email");
        user.setId(getNewUserId());
        return UserMapper.mapperToUserDTO(userRepository.saveUser(UserMapper.mapperToUser(user)));
    }
    //endregion

    //region getUserById
    @Override
    public UserDTO getUserById(long userId) {
        if (userRepository.getAllUsers()
                .stream()
                .map(User::getId)
                .anyMatch(id -> id == userId))
            return UserMapper.mapperToUserDTO(userRepository.getAllUsers()
                    .stream()
                    .filter(user -> user.getId() == userId)
                    .collect(Collectors.toList()).get(0));
        throw new NotFoundObjectException("The user is not found");
    }
    //endregion

    //region deleteUser
    @Override
    public boolean deleteUser(long userId) {
        if (userRepository.getAllUsers()
                .stream()
                .map(User::getId)
                .anyMatch(id -> id == userId))
            return userRepository.getAllUsers()
                    .remove(
                            userRepository.getAllUsers()
                                    .stream()
                                    .filter(user -> user.getId() == userId)
                                    .collect(Collectors.toList()).get(0));
        return false;
    }
    //endregion

    //region updateUser
    @Override
    public UserDTO updateUser(long userId, String params) throws JsonProcessingException{
        if (userRepository.getAllUsers()
                .stream()
                .map(User::getId)
                .noneMatch(id -> id == userId))
            throw new IllegalArgumentException("There is no the user");

        User user = userRepository.getAllUsers()
                .stream()
                .filter(u -> u.getId() == userId)
                .collect(Collectors.toList()).get(0);

        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);

        JsonNode jsonNode = objectMapper.readTree(params);
        if (jsonNode.has("name"))
            user.setName(jsonNode.get("name").asText());
        if (jsonNode.has("email")) {
            boolean containEmail = userRepository.getAllUsers()
                    .stream()
                    .map(User::getEmail)
                    .anyMatch(email -> email.equals(jsonNode.get("email").asText()));
            if (containEmail)
                throw new IllegalArgumentException("There is a user with such an email");
            user.setEmail(jsonNode.get("email").asText());
        }

        return UserMapper.mapperToUserDTO(user);
    }
    //endregion

    private long getNewUserId(){
        return newUserId++;
    }


}
