package pl.mswierczewski.inclouds.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.mswierczewski.inclouds.dtos.user.AdminSignUpRequest;
import pl.mswierczewski.inclouds.dtos.user.SignUpResponse;
import pl.mswierczewski.inclouds.dtos.user.UserSignUpRequest;
import org.springframework.stereotype.Service;
import pl.mswierczewski.inclouds.exceptions.api.UserException;
import pl.mswierczewski.inclouds.mappers.UserMapper;
import pl.mswierczewski.inclouds.models.User;
import pl.mswierczewski.inclouds.repositories.UserRepository;
import pl.mswierczewski.inclouds.services.UserService;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public SignUpResponse signUpAdminUser(AdminSignUpRequest signUpRequest) {
        if (existsByEmail(signUpRequest.getEmail())) {
            logger.trace("Someone tried to register with existing email ({}) in system!", signUpRequest.getEmail());
            throw UserException.EMAIL_IS_TAKEN;
        }

        User user = userMapper.mapAdminSignUpRequestToUser(signUpRequest);
        user.setEnabled(true); // TODO: after email verification
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        userRepository.save(user);

        logger.trace("New user (id:{}) has been successfully added!", user.getId());

        return userMapper.mapUserToSignUpResponse(user);

        // TODO: add email verification
    }

    @Override
    public SignUpResponse signUpUser(UserSignUpRequest signUpRequest) {
        if (existsByEmail(signUpRequest.getEmail())) {
            logger.trace("Someone tried to register with existing email ({}) in system!", signUpRequest.getEmail());
            throw UserException.EMAIL_IS_TAKEN;
        }

        User user = userMapper.mapUserSignUpRequestToUser(signUpRequest);
        user.setEnabled(true); // TODO: after email verification
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        userRepository.save(user);

        logger.trace("New user (id:{}) has been successfully signed up!", user.getId());

        return userMapper.mapUserToSignUpResponse(user);

        // TODO: add email verification
    }

    @Override
    public User getUserByEmail(String email) {
        return  userRepository
                .findByEmail(email)
                .orElseThrow(() -> UserException.USER_NOT_FOUND);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found!", username)));
    }
}
