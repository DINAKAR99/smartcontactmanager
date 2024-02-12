package cgg.smartcontactmanager.smartcontactmanager.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    public User u1;

    public CustomUserDetails(User u1) {
        this.u1 = u1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> auths = new HashSet<SimpleGrantedAuthority>();

        auths.add(new SimpleGrantedAuthority(u1.getRole()));

        return auths;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return u1.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return u1.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}