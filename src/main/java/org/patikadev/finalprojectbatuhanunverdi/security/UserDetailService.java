package org.patikadev.finalprojectbatuhanunverdi.security;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.patikadev.finalprojectbatuhanunverdi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByTc(username).orElseThrow(() -> new UsernameNotFoundException(username+" not found"));
        return new UserDetail(  user.getTc(),user.getPassword(),user.getUserStatus());
    }
}
