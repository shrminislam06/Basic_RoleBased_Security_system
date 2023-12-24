package com.springSecurity.LoginSystem.config;

import com.springSecurity.LoginSystem.model.OurUser;
import com.springSecurity.LoginSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OurUser>user=userRepository.findByMail(username);
        return  user.map(OurUserInfoDetais::new).orElseThrow(()->new UsernameNotFoundException("user name not found"));

    }
}
