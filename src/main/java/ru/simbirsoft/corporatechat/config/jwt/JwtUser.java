package ru.simbirsoft.corporatechat.config.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.simbirsoft.corporatechat.domain.enums.Role;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JwtUser implements UserDetails {

    private final Long id;

    private final String name;

    private final String password;

    private final Long roomId;

    private final Role role;

    private final boolean block;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Long getRoomId() {
        return roomId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !block;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
