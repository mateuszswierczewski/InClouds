package pl.mswierczewski.inclouds.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.mswierczewski.inclouds.dtos.user.AdminSignUpRequest;
import pl.mswierczewski.inclouds.dtos.user.SignUpResponse;
import pl.mswierczewski.inclouds.dtos.user.UserSignUpRequest;
import pl.mswierczewski.inclouds.models.User;

public interface UserService extends UserDetailsService {
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    SignUpResponse signUpAdminUser(AdminSignUpRequest signUpRequest);
    SignUpResponse signUpUser(UserSignUpRequest signUpRequest);
    User getUserByEmail(String username);
}
