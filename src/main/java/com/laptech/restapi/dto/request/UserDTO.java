package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private Gender gender;
    private String email;
    private LocalDate dateOfBirth;
    private String updateBy;

    /**
     * update All information of User
     */
    public static User transform(Map<String, String> request) {
        User user = new User();
        user.setId(0L);
        user.setName(request.get("name"));
        user.setGender(Gender.valueOf(request.get("gender")));
        user.setDateOfBirth(LocalDate.parse(request.get("dateOfBirth")));
        user.setPhone(request.get("phone"));
        user.setEmail(request.get("email"));
        user.setPassword(request.get("password"));
        user.setUpdateBy(request.get("updateBy"));

        if (request.containsKey("isActive"))
            user.setActive(Boolean.parseBoolean(request.get("isActive")));
        else
            user.setActive(true);
        return user;
    }

    /**
     * update some information of User
     * except Phone, created Date
     */
    public static UserDTO getData(Map<String, String> request) {
        UserDTO user = new UserDTO();
        user.setName(request.get("name"));
        user.setGender(Gender.valueOf(request.get("gender")));
        user.setEmail(request.get("email"));
        user.setDateOfBirth(LocalDate.parse(request.get("dateOfBirth")));
        return user;
    }
}
