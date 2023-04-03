package com.example.clinicapp.service;

import com.example.clinicapp.config.JwtService;
import com.example.clinicapp.dto.request.AuthenticationRequest;
import com.example.clinicapp.dto.response.AuthenticationResponse;
import com.example.clinicapp.entity.Token;
import com.example.clinicapp.entity.User;
import com.example.clinicapp.repository.TokenRepository;
import com.example.clinicapp.enums.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /*public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }*/

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userService.loadUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens((User) user); //aut olan userların logunu tutmus oldu.
        saveUserToken((User) user, jwtToken); //var olan token i geri döndürebiliriz.
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .createDate(new Date())
                .updateDate(new Date())
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setUpdateDate(new Date());
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
