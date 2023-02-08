package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @since 2023-02-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {
    private String name;
    private String icon;
    private String title;
    private String description;

    public static Label transform(LabelDTO dto) {
        Label label = new Label();
        label.setId(0L);
        label.setName(dto.getName());
        label.setIcon(dto.getIcon());
        label.setTitle(dto.getTitle());
        label.setDescription(dto.getDescription());
        return label;
    }
}
