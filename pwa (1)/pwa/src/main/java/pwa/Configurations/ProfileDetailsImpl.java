package pwa.Configurations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import pwa.Entities.Models.Profile;
import pwa.Entities.Models.Role;

public class ProfileDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -2859479868623108808L;
    private Profile profile;
    private List<GrantedAuthority> roles = new ArrayList<>();

    public ProfileDetailsImpl(Profile profile) {
        this.profile = profile;

        for (Role role : profile.getProfileRoles()) {
            GrantedAuthority auth = new SimpleGrantedAuthority(role.toString());
            roles.add(auth);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.profile.getPassword();
    }

    @Override
    public String getUsername() {
        return this.profile.getEmail();
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
        return profile.isEnabled();
    }

    public String getNickname() {
        return this.profile.getNickname();
    }
}
