package com.laptech.restapi.dto.filter;

import com.laptech.restapi.util.ConvertDate;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2023-02-23
 */
@Getter
@Setter
public class BannerFilter extends BaseFilter {
    private String path;
    private String type;
    private String title;
    private String linkProduct;
    private LocalDate usedDate;
    private LocalDate endedDate;

    public BannerFilter(String sortBy, String sortDir, LocalDate createdDate, LocalDate modifiedDate,
                        LocalDate deletedDate, Boolean isDel, String updateBy, String path, String type,
                        String title, String linkProduct, LocalDate usedDate, LocalDate endedDate) {
        super(sortBy, sortDir, createdDate, modifiedDate, deletedDate, isDel, updateBy);
        this.path = path;
        this.type = type;
        this.title = title;
        this.linkProduct = linkProduct;
        this.usedDate = usedDate;
        this.endedDate = endedDate;
    }

    public BannerFilter(Map<String, String> params) {
        super(params);
        this.path = params.get("path");
        this.type = params.get("type");
        this.title = params.get("title");
        this.linkProduct = params.get("linkProduct");
        this.usedDate = ConvertDate.getDateFromString(params.get("usedDate"));
        this.endedDate = ConvertDate.getDateFromString(params.get("endedDate"));
    }

    public Object[] getObject(boolean hasSort) {
        List<Object> objects = new ArrayList<>();
        objects.add(this.path);
        objects.add(this.type);
        objects.add(this.title);
        objects.add(this.linkProduct);
        objects.add(Date.valueOf(this.usedDate));
        objects.add(Date.valueOf(this.endedDate));
        objects.addAll(Arrays.asList(super.getObject(hasSort)));
        return objects.toArray();
    }
}
