package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.authority.AuthorityRequestDTO;
import nl.novi.smeticketapi.dtos.user.UserRequestDTO;
import nl.novi.smeticketapi.dtos.user.UserResponseDTO;
import nl.novi.smeticketapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //Constructor
    public UserController(UserService userService) {this.userService = userService;}

    //Endpoints
    //GET /users - Returns a list of all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //GET /users/{username} - Returns a user by username
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        UserResponseDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    //POST /users - Creates a new user
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserResponseDTO newUser = userService.createUser(requestDTO);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(newUser);
    }

    //PUT /users/{username} - Update an existing user
    @PutMapping("/{username}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String username, @RequestBody @Valid UserRequestDTO requestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(username, requestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE /users/{username} - Delete a user
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    //POST /users/{username}/authorities - Add a role to a user
    @PostMapping("/{username}/authorities")
    public ResponseEntity<Void> addUserAuthority(@PathVariable String username, @RequestBody @Valid AuthorityRequestDTO authorityRequestDTO) {
        userService.addUserAuthority(username, authorityRequestDTO);
        return ResponseEntity.noContent().build();
    }

    //DELETE /users/{username}/authorities/{authority} - Remove a role from a user
    @DeleteMapping("/{username}/authorities/{authority}")
    public ResponseEntity<Void> removeUserAuthority(@PathVariable String username, @PathVariable String authority) {
        userService.removeUserAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
