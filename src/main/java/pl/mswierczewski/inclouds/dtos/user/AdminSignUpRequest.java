package pl.mswierczewski.inclouds.dtos.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.mswierczewski.inclouds.models.enums.UserRole;
import pl.mswierczewski.inclouds.utils.LocalDateDeserializer;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class AdminSignUpRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 100)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String lastName;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Past
    private LocalDate birthDate;

    @NotNull
    private Set<UserRole> roles;

    private AdminSignUpRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
