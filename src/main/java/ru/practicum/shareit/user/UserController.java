package ru.practicum.shareit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public User saveNewUser(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDTO updateUser(@PathVariable long userId, @RequestBody String params) throws JsonProcessingException {
           return userService.updateUser(userId, params);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable long userId) {
        if(userService.deleteUser(userId))
            return true;
        else throw new IllegalArgumentException("There is no the user");
    }

}