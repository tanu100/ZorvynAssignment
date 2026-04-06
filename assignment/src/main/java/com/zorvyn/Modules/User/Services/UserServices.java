package com.zorvyn.Modules.User.Services;


import com.zorvyn.Modules.User.DTOs.UserDto;
import com.zorvyn.Modules.Shared.Exception.Errors.UserNotFoundException;
import com.zorvyn.Modules.User.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final com.zorvyn.Modules.User.Repository.JpaRepo table;

    private final PasswordEncoder passwordEncoder;

    public User FindById(Long Id){
        return table.findById(Id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

    }

    public List<User> Getusers(){
        return table.findAll();
    }

    public User CreateUser(UserDto userDto){
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        return table.save(user);
    }

    public User UpdateUser(Long Id,UserDto userDto){
        User user=FindById(Id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        return table.save(user);
    }

    public String DeleteUser(Long Id){
        User user=FindById(Id);
        table.delete(user);
        return "User with Id " + Id +" is Deleted";
    }

}
