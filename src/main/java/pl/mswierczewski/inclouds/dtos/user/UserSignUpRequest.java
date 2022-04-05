package pl.mswierczewski.inclouds.dtos.user;

import pl.mswierczewski.inclouds.models.enums.Nationality;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserSignUpRequest {

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
    @Past
    private LocalDate birthDate;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String phoneNumber;

    @NotNull
    @NotEmpty
    private Nationality nationality;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }
}
