package com.laptech.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laptech.restapi.common.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @version 2.0.0
 * @since 2022-11-18 (update 2023-02-02)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends SetupDate {
    private long id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    @JsonIgnore
    private String password;
    private boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id
                && isActive == user.isActive
                && name.equals(user.name)
                && gender == user.gender
                && dateOfBirth.equals(user.dateOfBirth)
                && phone.equals(user.phone)
                && Objects.equals(email, user.email)
                && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                "} " + super.toString();
    }
}
