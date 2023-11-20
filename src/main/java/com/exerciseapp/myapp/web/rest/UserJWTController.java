package com.exerciseapp.myapp.web.rest;

import com.exerciseapp.myapp.security.jwt.JWTFilter;
import com.exerciseapp.myapp.security.jwt.TokenProvider;
import com.exerciseapp.myapp.service.UserService;
import com.exerciseapp.myapp.service.dto.UserDTO;
import com.exerciseapp.myapp.service.response.ApiResponse;
import com.exerciseapp.myapp.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final UserService userService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(
        TokenProvider tokenProvider,
        UserService userService,
        AuthenticationManagerBuilder authenticationManagerBuilder
    ) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserDTO userDTO) {
        this.userService.register(userDTO);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
        if (authentication != null) {
            contextLogoutHandler.logout(request, response, authentication);
            if (contextLogoutHandler.isInvalidateHttpSession()) {
                authentication.setAuthenticated(false);
            }
        }

        return ResponseEntity.ok(ApiResponse.ok());
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
