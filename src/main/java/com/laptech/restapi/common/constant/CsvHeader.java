package com.laptech.restapi.common.constant;

public class CsvHeader {
    public final static String[] bannerHeader = {"ID", "Image's path", "Image type", "Title (show)", "Link product (click)", "Use date", "End date", "Created date", "Last updated date"};
    public final static String[] brandHeader = {"ID", "Brand's name", "Country", "Establish date", "Link logo", "Created date", "Last updated date", "Products"};
    public final static String[] categoryHeader = {"ID", "Name", "Image's link", "Description", "Created date", "Last updated date"};
    public final static String[] commentHeader = {"ID", "ID Reply", "ID Product", "User name", "Phone", "Content", "Created date", "Last updated date"};
    public final static String[] discountHeader = {"ID", "Code", "Rate sale", "Applied type", "Max price discount", "Applied date", "End date", "Created date", "Last updated date"};
    public final static String[] feedbackHeader = {"ID", "ID product", "ID user", "User name", "Content", "Rating point", "Created date", "Last updated date"};
    public final static String[] importProductHeader = {"ID", "ID product", "Quantity amount", "Import price", "Import date", "Created date", "Last updated date"};
    public final static String[] invoiceHeader = {"ID", "ID user", "User name", "Address", "Phone", "Product amount", "Ship cost", "Extract price", "Tax", "Last price", "Payment type", "Payment status", "Order status", "Note", "Created date", "Last updated date"};
    public final static String[] labelHeader = {"ID", "Name", "Title", "Description (clear)", "Created date", "Last updated date"};
    public final static String[] productHeader = {"ID", "Brand name", "Category", "Product name", "Released date", "Quantity in stock", "Listed price", "Created date", "Last updated date"};
    public final static String[] roleHeader = {"ID", "Name", "Description", "Created date", "Last updated date", "User list"};
    public final static String[] userHeader = {"ID", "Full name", "Gender", "Date of birth", "Phone", "Email", "Account status", "Created date", "Last updated date", "Roles"};
}
