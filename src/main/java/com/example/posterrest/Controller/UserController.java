package com.example.posterrest.Controller;

import com.example.posterrest.Entity.User;
import com.example.posterrest.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



//        @GetMapping("/hello")
//        public ResponseEntity<String> sayHello() {
//            String message = "Hello, World!";
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        }
//
//
//        @PostMapping("/create")
//        public ResponseEntity<String> createEntity(@RequestBody String requestBody) {
//            // Логика обработки запроса и создания сущности
//            requestBody = requestBody + "Entity created successfully";
//            return new ResponseEntity<>(requestBody, HttpStatus.CREATED);
//        }
//    }



    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/{id}/photo")
        public ResponseEntity<String> uploadPhoto(
                @PathVariable Long id,
                @RequestParam("file") MultipartFile file) {
            try {
                userService.uploadPhoto(id, file.getBytes());
                return ResponseEntity.ok("Photo uploaded successfully");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to upload photo");
            }
        }

    }


