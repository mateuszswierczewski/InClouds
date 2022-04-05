package pl.mswierczewski.inclouds.mappers;

import pl.mswierczewski.inclouds.dtos.user.AdminSignUpRequest;
import pl.mswierczewski.inclouds.dtos.user.SignUpResponse;
import pl.mswierczewski.inclouds.dtos.user.UserResponse;
import pl.mswierczewski.inclouds.dtos.user.UserSignUpRequest;
import pl.mswierczewski.inclouds.models.User;

public interface UserMapper {

    User mapAdminSignUpRequestToUser(AdminSignUpRequest request);
    User mapUserSignUpRequestToUser(UserSignUpRequest request);
    SignUpResponse mapUserToSignUpResponse(User user);
    UserResponse mapToUserResponse(User user);
}
