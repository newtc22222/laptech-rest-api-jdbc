package com.laptech.restapi.service.email;

import javax.servlet.http.HttpServletRequest;

public interface EmailService {
    void sendPasswordResetEmail(HttpServletRequest request, String passwordResetUrl, String token, String toEmail);
    void sendInvoiceToEmail(String toEmail, String invoiceId);
}

