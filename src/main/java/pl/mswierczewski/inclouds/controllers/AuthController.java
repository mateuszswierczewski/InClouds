package pl.mswierczewski.inclouds.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mswierczewski.inclouds.dtos.auth.AuthenticationRequest;
import pl.mswierczewski.inclouds.dtos.auth.AuthenticationResponse;
import pl.mswierczewski.inclouds.services.AuthService;

import javax.validation.Valid;

import static pl.mswierczewski.inclouds.services.impl.DefaultAuthService.AUTHORIZATION_HEADER;
import static pl.mswierczewski.inclouds.services.impl.DefaultAuthService.AUTHORIZATION_PREFIX;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authService.authenticate(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_PREFIX + response.getToken())
                .body(response);
    }

    @PostMapping("/invalidate-token")
    public ResponseEntity<?> invalidateToken() {
        throw new UnsupportedOperationException();
    }
}
