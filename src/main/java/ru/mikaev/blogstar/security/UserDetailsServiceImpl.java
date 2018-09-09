package ru.mikaev.blogstar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.mikaev.blogstar.dao.UserRepository;
import ru.mikaev.blogstar.entities.User;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByUsername(username);
        return new UserDetailsImpl(user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
    }
}
