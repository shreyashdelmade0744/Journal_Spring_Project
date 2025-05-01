package sdd.justcode.journal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.repository.UserEntryRepo;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryRepo userEntryRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntryRepo.findByUsername(username);
        if(user!=null){
            UserDetails userDetails = User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();

            return userDetails;
        }

        throw new UsernameNotFoundException("user not found with username: " + username);
    }
}

