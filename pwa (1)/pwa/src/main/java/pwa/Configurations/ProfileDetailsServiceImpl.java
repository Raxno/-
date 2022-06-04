package pwa.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import pwa.Repositories.ProfileStorage;
import pwa.Entities.Models.Profile;

@Service
public class ProfileDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ProfileStorage profileStorage;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileStorage.findByEmailAndEnabledTrue(username);
        return new ProfileDetailsImpl(profile);
    }

}
