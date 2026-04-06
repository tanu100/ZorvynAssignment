package com.zorvyn.Modules.User.Controllers;


import com.zorvyn.Modules.User.DTOs.UserDto;
import com.zorvyn.Modules.User.Models.User;
import com.zorvyn.Modules.User.Services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServices services;

    @GetMapping()
    public ResponseEntity<List<User>> GetUsers(){
        return new ResponseEntity<>(services.Getusers(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> CreateUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(services.CreateUser(userDto),HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<User> GetById(@PathVariable Long Id){
        return new ResponseEntity<>(services.FindById(Id),HttpStatus.OK);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<User> UpdateById(@PathVariable Long Id,@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(services.UpdateUser(Id,userDto),HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> DeleteById(@PathVariable Long Id){
        return new ResponseEntity<>(services.DeleteUser(Id),HttpStatus.OK);
    }
}
