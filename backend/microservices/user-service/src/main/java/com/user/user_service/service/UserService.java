package com.user.user_service.service;

import com.user.user_service.dto.ClientUser;
import com.user.user_service.feign.ClientInterface;
import com.user.user_service.feign.FreelancerInterface;
import com.user.user_service.model.User;
import com.user.user_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ClientInterface clientInterface;
    private final FreelancerInterface freelancerInterface;

    public User saveUser(User user) {
        try {
            userRepository.save(user);
            if(user.getRole().name().equals("FREELANCER")){
                freelancerInterface.createFreelancer(
                        new com.user.user_service.dto.FreelancerUser(
                                user.getId(),
                                user.getName(),
                                user.getUserName(),
                                user.getPassword()
                        ));
                System.out.println("Freelancer created");

            } else if(user.getRole().name().equals("CLIENT")) {
                ClientUser clientUser = clientInterface.createClient(
                        new ClientUser(
                                user.getId(),
                                user.getName(),
                                user.getUserName(),
                                user.getPassword()
                        ));
                System.out.println("Client created: " + clientUser);
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserRole(String email) {
        Optional<Object> user = userRepository.findByUserName(email);
        User u = (User) user.get();
        System.out.println(user);
        if (user != null) {
            return u.getRole().name();
        } else {
            return "User not found";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Object> user = Optional.ofNullable(userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        User u = (User) user.get();
        return new org.springframework.security.core.userdetails.User(
                u.getUserName(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))
        );
    }
}
