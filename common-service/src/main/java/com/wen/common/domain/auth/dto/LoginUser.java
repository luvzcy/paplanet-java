package com.wen.common.domain.auth.dto;


import com.wen.common.domain.user.Userinfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private Userinfo userinfo;

    //不需要序列化
    private transient List<GrantedAuthority> authorityList;

    private List<String> authorities;

    public LoginUser(Userinfo userinfo, List<String> authorities){
        this.userinfo = userinfo;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorityList = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorityList;
    }

    @Override
    public String getPassword() {
        return userinfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userinfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
