package com.zorvyn.Modules.Services;


import com.zorvyn.Modules.User.DTOs.UserDto;
import com.zorvyn.Modules.User.Exception.UserNotFoundException;
import com.zorvyn.Modules.User.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final com.zorvyn.Modules.User.Repository.JpaRepo table;

    public ResponseEntity<User> FindById(Long Id){
        return new ResponseEntity<>(table.findById(Id).orElseThrow(()->
                new UserNotFoundException("User Not found in database")),
                HttpStatus.OK
        );
    }

    public List<User> Getusers(){
        return table.findAll();
    }

    public User CreateUser(UserDto userDto){
        User user=new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        return table.save(user);
    }

    public User UpdateUser(Long Id,UserDto userDto){
        User user=FindById(Id).getBody();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        return table.save(user);
    }

    public String DeleteUser(Long Id){
        User user=FindById(Id).getBody();
        table.delete(user);
        return "User with Id " + Id +" is Deleted";
    }

}
