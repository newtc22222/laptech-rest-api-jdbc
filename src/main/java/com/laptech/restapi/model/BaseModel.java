package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Include: created_date, modified_date, deleted_date, is_del, update_by
 *
 * @author Nhat Phi
 * @since 2023-02-02
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;
    private boolean isDel;
    private String updateBy;

    public void setData(BaseModel model) {
        this.createdDate = model.getCreatedDate();
        this.modifiedDate = model.getModifiedDate();
        this.deletedDate = model.getDeletedDate();
        this.isDel = model.isDel();
        this.updateBy = model.getUpdateBy();
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", deletedDate=" + deletedDate +
                ", isDel=" + isDel +
                ", updateBy=" + updateBy +
                '}';
    }
}
