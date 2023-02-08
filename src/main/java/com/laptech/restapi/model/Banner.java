package com.laptech.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Nhat Phi
 * @since 2022-11-20 (update 2023-02-02)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banner extends SetupDate {
    private long id;
    private String path;
    private String type;
    private LocalDate usedDate;
    private LocalDate endedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return id == banner.id
                && path.equals(banner.path)
                && type.equals(banner.type)
                && usedDate.equals(banner.usedDate)
                && endedDate.equals(banner.endedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", usedDate=" + usedDate +
                ", endedDate=" + endedDate +
                "} " + super.toString();
    }
}
