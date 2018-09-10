package ru.mikaev.blogstar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.mikaev.blogstar.dao.UsersRepository;
import ru.mikaev.blogstar.entities.User;
import ru.mikaev.blogstar.services.UsersService;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersService.findOneByUsername(username);
        return new UserDetailsImpl(user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
    }
}
