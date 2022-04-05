package pl.mswierczewski.inclouds.services.impl;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import pl.mswierczewski.inclouds.dtos.auth.AuthenticationRequest;
import pl.mswierczewski.inclouds.mappers.UserMapper;
import pl.mswierczewski.inclouds.models.User;
import pl.mswierczewski.inclouds.services.UserService;

@ExtendWith(MockitoExtension.class)
class DefaultAuthServiceTest {

    private DefaultAuthService underTest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;



    @Test
    void itShouldAuthenitcateUser() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@test.com");
        request.setPassword("testtest");

        User user = new User("test@test.com", "testtest");
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

    }

}