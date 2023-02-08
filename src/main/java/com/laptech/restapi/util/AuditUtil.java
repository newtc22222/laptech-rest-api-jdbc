package com.laptech.restapi.util;

public class AuditUtil {
    public static String getPhoneAudit(String phone) {
        StringBuilder error = new StringBuilder();
        if (phone.length() < 9 || phone.length() > 15) {
            error
                    .append("Invalid phone: length")
                    .append((phone.length() < 9) ? " minimum is 9" : " maximum is 15");
        }
        if (!phone.matches("^[/d+]{9,15}")) {
            error.append("Invalid phone: Phone must be included + in first and number only!");
        }
        return error.toString();
    }

    /**
     * <a href="https://www.tutorialspoint.com/checking-for-valid-email-address-using-regular-expressions-in-java"> RegEx email</a>
     */
    public static String getEmailAudit(String email) {
        if (!email.matches("^[a-zA-Z/d+_.-]+@[a-zA-Z/d.-]+$")) {
            return "Invalid email: Please check this email again!";
        }
        return "";
    }

    public static String getStringAudit(String text, String fieldName, int maxLength) {
        if (text == null || text.isEmpty()) {
            return fieldName + " field cannot empty!";
        }
        if (text.length() > maxLength) {
            return fieldName + " field has maximum length is " + maxLength + "!";
        }
        return "";
    }

    public static <T extends Number> String getNumberAudit(T value, String fieldName, boolean isUnsigned, T minValue, T maxValue) {
        if (isUnsigned && value.doubleValue() < 0) {
            return fieldName + " invalid! [Unsigned value!]";
        }
        if (value.doubleValue() < minValue.doubleValue()) {
            return fieldName + " invalid! [minimum value is: " + minValue + "]";
        }
        if (value.doubleValue() > maxValue.doubleValue()) {
            return fieldName + " invalid! [maximum value is: " + maxValue + "]";
        }

        return "";
    }
}
