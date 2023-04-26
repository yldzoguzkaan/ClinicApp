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

import java.util.Calendar;
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


    /** Kullanıcı sistemden hiç token almadıysa token üretilir gönderilir. Eğer mevcutta token varsa;
     ** Token üretildiği zamandan 2 saat geçmişse o token öldürülür ve yenisi üretilir.
     ** Aksi durumda mevcut token döndürülür. Mevcut tokenin geçerliliğine 2 saat daha eklenir.
    **/
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = (User) userService.loadUserByUsername(request.getUsername());
        Token token = tokenRepository.findByUserIdAndIsActiveTrueAndIsDeleteFalse(user.getId()).orElse(null);
        var jwtToken = jwtService.generateToken(user);
        if (token == null)
        {
            token = saveUserToken(user,jwtToken);
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(token.getUpdateDate());
            calendar.add(Calendar.HOUR_OF_DAY, 2);
            Date newDate = calendar.getTime();
            if(new Date().after(newDate))
            {
                revokeUserToken(token);
                token = saveUserToken(user,jwtToken);
            }
            else
            {
                updateUserToken(token);
            }
        }
        return AuthenticationResponse.builder()
                .uid(user.getUid())
                .nameSurname(user.getNameSurname())
                .username(user.getUsername())
                .role(user.getRole())
                .permissions(user.getPermissions())
                .token(token.getToken())
                .build();

    }

    private Token saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setIsActive(true);
        token.setIsDelete(false);
        return tokenRepository.save(token);
    }

    private void updateUserToken(Token token){
        token.setUpdateDate(new Date());
        tokenRepository.save(token);
    }

    private void revokeUserToken(Token token){
        token.setUpdateDate(new Date());
        token.setIsActive(false);
        token.setIsDelete(true);
        tokenRepository.save(token);
    }

}
