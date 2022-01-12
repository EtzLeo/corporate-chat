package ru.simbirsoft.corporatechat.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.repository.UserRepository;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        JwtUser jwtUser = new JwtUser(user.getId(), user.getName(), user.getPassword(),
                new ArrayList<>(user
                        .getUserRooms()
                        .stream()
                        .map(userRoom -> new SimpleGrantedAuthority(userRoom.getRole().name()))
                        .collect(Collectors.toList())));
        return jwtUser;
    }

}
