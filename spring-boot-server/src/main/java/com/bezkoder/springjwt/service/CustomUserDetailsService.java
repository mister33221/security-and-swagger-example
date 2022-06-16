package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.CustomUserDetails;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    // your UserRepository for your user
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUseruser = userRepository.findByUsername(username);
        System.out.println("optionalUseruser.get().getUsername():"+optionalUseruser.get().getUsername());
        if (null == optionalUseruser.get() || ! optionalUseruser.get().getUsername().equals(username)) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {

            return new CustomUserDetails(optionalUseruser.get());
        }
    }
}