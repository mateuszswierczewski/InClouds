package pl.mswierczewski.inclouds.services;

import pl.mswierczewski.inclouds.dtos.auth.AuthenticationRequest;
import pl.mswierczewski.inclouds.dtos.auth.AuthenticationResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    void authorizeRequest(HttpServletRequest httpServletRequest);
    String refreshToken(HttpServletRequest httpServletRequest);
}
