package com.laptech.restapi.dto.request;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.model.User;
import com.laptech.restapi.util.ConvertDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-21
 */
@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String name;
    @ApiModelProperty(required = true, example = "MALE | FEMALE | OTHER")
    @NotEmpty
    private String gender;
    @ApiModelProperty(required = true, example = "2000-01-01")
    @NotEmpty
    private String dateOfBirth;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(min = 10, max = 15)
    private String phone;
    @Email
    private String email;
    @ApiModelProperty(required = true, example = "Anh8^&^5d")
    @NotEmpty
    @Size(min = 8, max = 50)
    private String password;
    @Size(max = 100)
    private String updateBy;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String gender, String dateOfBirth, String phone,
                   String email, String password, String updateBy) {
        this.id = (id == null) ? 0L : id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.updateBy = updateBy;
    }

    /**
     * update All information of User
     */
    public static User transform(UserDTO dto) {
        User user = new User();
        user.setId((dto.getId() != null) ? dto.getId() : 0L);
        user.setName(dto.getName());
        try {
            user.setGender(Gender.valueOf(dto.getGender()));
        } catch (IllegalArgumentException err) {
            user.setGender(Gender.MALE);
        }
        user.setDateOfBirth(ConvertDate.getLocalDateFromString(dto.getDateOfBirth()));
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail() == null ? null : dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUpdateBy(dto.getUpdateBy());
        return user;
    }

    /**
     * update some information of User
     * except Phone, created Date
     */
    public static UserDTO getData(Map<String, String> request) {
        UserDTO user = new UserDTO();
        user.setName(request.get("name"));
        try {
            user.setGender(request.get("gender"));
        } catch (IllegalArgumentException err) {
            user.setGender(Gender.MALE.toString());
        }
        if (request.containsKey("email")) {
            user.setEmail(request.get("email") == null ? null : request.get("email"));
        }
        user.setDateOfBirth(request.get("dateOfBirth"));
        user.setUpdateBy(request.get("updateBy"));
        return user;
    }
}
