package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDTO saveNewUser(@RequestBody @Valid UserDTO user) {
        return userService.saveUser(user);
    }

    @PatchMapping("/{userId}")
    public UserDTO updateUser(@PathVariable long userId,
                              @RequestBody UserDTO userDTO){
           return userService.updateUser(userId, userDTO);
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