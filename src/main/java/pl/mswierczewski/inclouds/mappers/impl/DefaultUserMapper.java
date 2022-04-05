package pl.mswierczewski.inclouds.mappers.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mswierczewski.inclouds.dtos.user.AdminSignUpRequest;
import pl.mswierczewski.inclouds.dtos.user.SignUpResponse;
import pl.mswierczewski.inclouds.dtos.user.UserResponse;
import pl.mswierczewski.inclouds.dtos.user.UserSignUpRequest;
import pl.mswierczewski.inclouds.mappers.UserMapper;
import pl.mswierczewski.inclouds.models.User;
import pl.mswierczewski.inclouds.models.UserExtendInformation;
import pl.mswierczewski.inclouds.models.enums.UserRole;

@Component
public class DefaultUserMapper implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    public DefaultUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User mapAdminSignUpRequestToUser(AdminSignUpRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        UserExtendInformation extendInformation = new UserExtendInformation();
        extendInformation.setFirstName(request.getFirstName());
        extendInformation.setLastName(request.getLastName());
        extendInformation.setBirthDate(request.getBirthDate());

        user.setUserInformation(extendInformation);

        request.getRoles().forEach(user::addRole);

        return user;
    }

    @Override
    public User mapUserSignUpRequestToUser(UserSignUpRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addRole(UserRole.PASSENGER);

        UserExtendInformation extendInformation = new UserExtendInformation();
        extendInformation.setFirstName(request.getFirstName());
        extendInformation.setLastName(request.getLastName());
        extendInformation.setBirthDate(request.getBirthDate());
        extendInformation.setPhoneNumber(request.getPhoneNumber());
        extendInformation.setNationality(request.getNationality());

        user.setUserInformation(extendInformation);

        return user;
    }

    @Override
    public SignUpResponse mapUserToSignUpResponse(User user) {
        SignUpResponse response = new SignUpResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getUserInformation().getFirstName());
        response.setLastName(user.getUserInformation().getLastName());
        response.setBirthDate(user.getUserInformation().getBirthDate());
        response.setRoles(user.getRoles());

        return  response;
    }

    @Override
    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getUserInformation().getFirstName());
        userResponse.setLastName(user.getUserInformation().getLastName());
        userResponse.setRoles(user.getRoles());

        return userResponse;
    }
}
