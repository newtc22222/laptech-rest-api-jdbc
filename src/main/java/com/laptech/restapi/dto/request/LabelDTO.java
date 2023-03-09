package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Label;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @since 2023-02-07
 */
@Getter
@Setter
@NoArgsConstructor
public class LabelDTO {
    private Long id;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String name;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String icon;
    @ApiModelProperty(required = true)
    @NotEmpty
    private String title;
    private String description;
    @Size(max = 100)
    private String updateBy;

    public LabelDTO(Long id, String name, String icon, String title, String description, String updateBy) {
        this.id = (id == null) ? 0L : id;
        this.name = name;
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.updateBy = updateBy;
    }

    public static Label transform(LabelDTO dto) {
        Label label = new Label();
        label.setId((dto.getId() != null) ? dto.getId() : 0L);
        label.setName(dto.getName());
        label.setIcon(dto.getIcon());
        label.setTitle(dto.getTitle());
        label.setDescription(dto.getDescription());
        label.setUpdateBy(dto.getUpdateBy());
        return label;
    }
}
