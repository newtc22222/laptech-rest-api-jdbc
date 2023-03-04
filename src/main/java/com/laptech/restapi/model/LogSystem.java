package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogSystem {
    private long id;
    private String actionTable;
    private String recordId;
    private LocalDateTime actionTime;
    private String actionBy;
    private String actionName;

    @Override
    public String toString() {
        return "LogSystem{" +
                "id=" + id +
                ", actionTable='" + actionTable + '\'' +
                ", recordId='" + recordId + '\'' +
                ", actionDate=" + actionTime +
                ", actionBy='" + actionBy + '\'' +
                ", actionName='" + actionName + '\'' +
                '}';
    }
}
