package com.FYP.Fleet.Controllers;

import com.FYP.Fleet.Dto.Request.UserRequestDto;
import com.FYP.Fleet.Dto.Response.UserResponseDto;
import com.FYP.Fleet.Models.User;
import com.FYP.Fleet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto){
        User user = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable long userId){
        UserResponseDto user = userService.getUserResponseById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
