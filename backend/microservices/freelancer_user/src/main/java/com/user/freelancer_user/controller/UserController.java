package com.user.freelancer_user.controller;

import com.user.freelancer_user.config.JwtUtil;
import com.user.freelancer_user.model.AuthRequest;
import com.user.freelancer_user.model.User;
import com.user.freelancer_user.repo.UserRepository;
import com.user.freelancer_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<? extends Object> registerUser(@RequestBody User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with the same username and try with different username");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userService.saveUser(user);
            if (savedUser != null) {
                return ResponseEntity.ok(savedUser);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/role")
    public ResponseEntity<String> getUserRole(@RequestParam String email) {
        // Placeholder implementation
        String role = userService.getUserRole(email);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // ✅ Get role from authenticated principal
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER"); // default fallback
            System.out.println("============="+role);
            String token = jwtUtil.generateToken(request.getUserName(), role);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access");
        }
    }

}
