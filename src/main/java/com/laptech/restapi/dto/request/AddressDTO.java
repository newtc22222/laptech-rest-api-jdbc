package com.laptech.restapi.dto.request;

import com.laptech.restapi.model.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * @author Nhat Phi
 * @since 2023-03-05
 */
@Getter
@Setter
public class AddressDTO {
    @Size(max = 25)
    private String id;
    @NotEmpty
    private Long userId;
    @ApiModelProperty(required = true, example = "Viet Nam")
    @NotEmpty
    @Size(min = 2, max = 25, message = "Country must be pass!")
    private String country;
    @ApiModelProperty(required = true, example = "Ho Chi Minh city")
    @NotEmpty
    @Size(min = 2, max = 25, message = "You need to provide information of Big City or Province!")
    private String line1;
    @ApiModelProperty(required = true, example = "Thu Duc City")
    @NotEmpty
    @Size(min = 2, max = 25, message = "You need to provide information of city, district or town!")
    private String line2;
    @ApiModelProperty(required = true, example = "Linh Trung ward")
    @NotEmpty
    @Size(min = 2, max = 25, message = "You need to provide information of commune!")
    private String line3;
    @ApiModelProperty(required = true, example = "64/1L, 17th street")
    @NotEmpty
    @Size(min = 2, max = 100, message = "You need to supply the place where get goods!")
    private String street;
    private Boolean isDefault;
    @Size(max = 100)
    private String updateBy;

    public AddressDTO() {}

    public AddressDTO(String id, long userId, String country, String line1, String line2, String line3,
                      String street, Boolean isDefault, String updateBy) {
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        this.userId = userId;
        this.country = country;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.street = street;
        this.isDefault = isDefault;
        this.updateBy = updateBy;
    }

    public static Address transform(AddressDTO dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setCountry(dto.getCountry());
        address.setLine1(dto.getLine1());
        address.setLine2(dto.getLine2());
        address.setLine3(dto.getLine3());
        address.setStreet(dto.getStreet());
        address.setDefault(dto.getIsDefault());
        address.setUpdateBy(dto.getUpdateBy());
        return address;
    }
}
