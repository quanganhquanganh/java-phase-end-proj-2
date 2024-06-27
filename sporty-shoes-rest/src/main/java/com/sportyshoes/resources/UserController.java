package com.sportyshoes.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sportyshoes.dto.PasswordChangeDTO;
import com.sportyshoes.dto.UserDTO;
import com.sportyshoes.model.User;
import com.sportyshoes.service.AuthenticationService;
import com.sportyshoes.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public UserController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/change-password")
    public ResponseEntity<UserDTO> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        authenticationService.changePassword(user.getUsername(), passwordChangeDto);

        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String term) {
        return ResponseEntity.ok(userService.searchUsers(term));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}