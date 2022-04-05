package pl.mswierczewski.inclouds.dtos.user;

import pl.mswierczewski.inclouds.models.enums.UserRole;

import java.time.LocalDate;
import java.util.Set;

public class SignUpResponse {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Set<UserRole> roles;

    public SignUpResponse() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
