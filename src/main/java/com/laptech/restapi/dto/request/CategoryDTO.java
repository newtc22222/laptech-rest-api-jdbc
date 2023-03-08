package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Nhat Phi
 * @since 2023-03-05
 */
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    @ApiModelProperty(required = true, example = "Monitor")
    @NotEmpty
    @Size(max = 50)
    private String name;
    @ApiModelProperty(required = true)
    @NotEmpty
    @Size(max = 50)
    private String image;
    @Size(max = 255)
    private String description;
    @Size(max = 100)
    private String updateBy;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, String image, String description, String updateBy) {
        this.id = (id == null) ? 0L : id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.updateBy = updateBy;
    }

    public static Category transform(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setImage(dto.getImage());
        category.setDescription(dto.getDescription());
        category.setUpdateBy(dto.getUpdateBy());
        return category;
    }
}
