package com.zorvyn.Modules.Security;

import com.zorvyn.Modules.Shared.Exception.Errors.UserNotFoundException;
import com.zorvyn.Modules.User.Models.User;
import com.zorvyn.Modules.User.Repository.JpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final JpaRepo table;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = table.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
