package com.laptech.restapi.audit;

import com.laptech.restapi.model.Address;

import java.util.ArrayList;
import java.util.List;

import static com.laptech.restapi.util.AuditUtil.getStringAudit;

/**
 * @since 2023-02-06
 */

public class AddressAudit {

    public static String getInvalidArguments(Address address) {
        List<String> errorList = new ArrayList<>();

        errorList.add(getStringAudit(address.getCountry(), "Country", 25));
        errorList.add(getStringAudit(address.getLine1(), "Province/City", 25));
        errorList.add(getStringAudit(address.getLine2(), "Town/District", 25));
        errorList.add(getStringAudit(address.getLine3(), "Commune", 25));
        errorList.add(getStringAudit(address.getStreet(), "Street", 100));

        return errorList.stream().reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
}
