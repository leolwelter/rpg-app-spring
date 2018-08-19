package com.lw.dmappserver.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserRepository repo;
    private PasswordEncoder encoder;

    // constructor injection preferred
    public UserController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // User registration
    @RequestMapping(method=RequestMethod.POST, value = "/users")
    public ResponseEntity createUser(@RequestBody User newUser) {
        if (newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty()) {
            System.out.println("User Not Created");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }

        newUser.setPassword(this.encoder.encode(newUser.getPassword()));
        newUser = repo.insert(newUser);
        System.out.println("User created: " + newUser.toString());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(newUser);
    }

    // User Login
    @RequestMapping(method=RequestMethod.POST, value = "/users/login")
    public ResponseEntity<User> loginUser(@RequestBody User userToLogin) {
        if (userToLogin == null || userToLogin.getUsername().isEmpty() || userToLogin.getPassword().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());

        User foundUser = repo.findUserByUsername(userToLogin.getUsername());

        if (foundUser == null
                || !foundUser.getUsername().equals(userToLogin.getUsername())
                || !encoder.matches(userToLogin.getPassword(), foundUser.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());

        System.out.println("Logging in " +
                foundUser.getUsername() + " :: " +
                foundUser.getPassword() + " :: " + foundUser.get_id());
        return ResponseEntity
                .ok(userToLogin);
    }

    @RequestMapping(method=RequestMethod.GET, value = "/users/{id}")
    public Optional<User> findById(@PathVariable String id) {
        return repo.findById(id);
    }

    @RequestMapping(method=RequestMethod.GET, value ="/users/all")
    public List<User> findAll() {
        return repo.findAll();
    }
}
