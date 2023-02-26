package com.example.Todolist.Auth;

import com.example.Todolist.Config.JwtService;
import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.User.Role;
import com.example.Todolist.User.User;
import com.example.Todolist.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) throws AlreadyDefined {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        System.out.println(userRepository.findUserByEmail(user.getEmail()));
        System.out.println(userRepository.findUserByFirstname(user.getFirstname()));
        System.out.println(userRepository.findUserByLastname(user.getLastname()));
        if (userRepository.findUserByEmail(user.getEmail()).isEmpty() && userRepository.findUserByFirstname(user.getFirstname()) == null && userRepository.findUserByLastname(user.getLastname()) == null) {
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        else {
            throw new AlreadyDefined("This user already exists");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()
            )
    );
    var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
