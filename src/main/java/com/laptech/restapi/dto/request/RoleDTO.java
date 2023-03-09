package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Nhat Phi
 * @since 2023-03-05
 */
@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {
    private Integer id;
    @ApiModelProperty(required = true, example = "STAFF")
    @NotEmpty
    @Size(min = 3, max = 25)
    private String name;
    private String description;
    @Size(max = 100)
    private String updateBy;

    public RoleDTO(Integer id, String name, String description, String updateBy) {
        this.id = (id == null) ? 0 : id;
        this.name = name;
        this.description = description;
        this.updateBy = updateBy;
    }

    public static Role transform(RoleDTO dto) {
        Role role = new Role();
        role.setId((dto.getId() != null) ? dto.getId() : 0);
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setUpdateBy(dto.getUpdateBy());
        return role;
    }
}
