package pl.mswierczewski.inclouds.controllers;

import pl.mswierczewski.inclouds.dtos.user.AdminSignUpRequest;
import pl.mswierczewski.inclouds.dtos.user.SignUpResponse;
import pl.mswierczewski.inclouds.dtos.user.UserSignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mswierczewski.inclouds.services.UserService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin-sign-up")
    public ResponseEntity<SignUpResponse> signUpAdminUser(@Valid @RequestBody AdminSignUpRequest signUpRequest) {
        var response = userService.signUpAdminUser(signUpRequest);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @PostMapping("/user-sign-up")
    public ResponseEntity<SignUpResponse> signUpUser(@Valid @RequestBody UserSignUpRequest signUpRequest) {
        var response = userService.signUpUser(signUpRequest);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity
                .status(OK)
                .body("git majezon");
    }


}
