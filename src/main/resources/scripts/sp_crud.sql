use youtube2_laptech;
DELIMITER $$

CREATE PROCEDURE sp_FindAllAddress()
BEGIN
SELECT * FROM tbl_address;
END$$

CREATE PROCEDURE sp_FindAddressById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_address WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAddressByUserId(IN in_user_id bigint)
BEGIN
SELECT * FROM tbl_address WHERE user_id=in_user_id;
END$$

CREATE PROCEDURE sp_InsertNewAddress(IN in_user_id bigint, IN in_country varchar(25), IN in_line1 varchar(25), IN in_line2 varchar(25), IN in_line3 varchar(25), IN in_street varchar(100), IN in_is_default tinyint(1))
BEGIN
INSERT INTO tbl_address (user_id, country, line1, line2, line3, street, is_default) VALUES (in_user_id, in_country, in_line1, in_line2, in_line3, in_street, in_is_default); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateAddress(IN in_id bigint(20), IN in_user_id bigint(20), IN in_country varchar(25), IN in_line1 varchar(25), IN in_line2 varchar(25), IN in_line3 varchar(25), IN in_street varchar(100), IN in_is_default tinyint(1))
BEGIN
UPDATE tbl_address SET user_id=in_user_id, country=in_country, line1=in_line1, line2=in_line2, line3=in_line3, street=in_street, is_default=in_is_default WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteAddress(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_address WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllBanners()
BEGIN
SELECT * FROM tbl_banner;
END$$

CREATE PROCEDURE sp_FindAllBannersLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_banner LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindBannerById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_banner WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindBannerByType(IN in_type varchar(255))
BEGIN
SELECT * FROM tbl_banner WHERE type=in_type;
END$$

CREATE PROCEDURE sp_InsertNewBanner(IN in_path varchar(255), IN in_type varchar(50), IN in_title varchar(100), IN in_link_product varchar(255), IN in_used_date date, IN in_ended_date date)
BEGIN
INSERT INTO tbl_banner (path, type, title, link_product, used_date, ended_date) VALUES (in_path, in_type, in_title, in_link_product, in_used_date, in_ended_date); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateBanner(IN in_id bigint(20), IN in_path varchar(255), IN in_type varchar(50), IN in_title varchar(100), IN in_link_product varchar(255), IN in_used_date date, IN in_ended_date date)
BEGIN
UPDATE tbl_banner SET path=in_path, type=in_type, title=in_title, link_product=in_link_product, used_date=in_used_date, ended_date=in_ended_date WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteBanner(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_banner WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllBrands()
BEGIN
SELECT * FROM tbl_brand;
END$$

CREATE PROCEDURE sp_FindAllBrandsLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_brand LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindBrandById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_brand WHERE id=in_id;
END$$

CREATE PROCEDURE sp_InsertNewBrand(IN in_name varchar(100), IN in_country varchar(100), IN in_establish_date date, IN in_logo varchar(255))
BEGIN
INSERT INTO tbl_brand (name, country, establish_date, logo) VALUES (in_name, in_country, in_establish_date, in_logo); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateBrand(IN in_id bigint(20), IN in_name varchar(100), IN in_country varchar(100), IN in_establish_date date, IN in_logo varchar(255))
BEGIN
UPDATE tbl_brand SET name=in_name, country=in_country, establish_date=in_establish_date, logo=in_logo WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteBrand(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_brand WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindCartByUserId(IN in_user_id bigint(20))
BEGIN
SELECT * FROM tbl_cart WHERE user_id=in_user_id;
END$$

CREATE PROCEDURE sp_InsertNewCart(IN in_id varchar(50), IN in_user_id bigint(20), IN in_discount_id bigint(20))
BEGIN
INSERT INTO tbl_cart (id, user_id, discount_id) VALUES (in_id, in_user_id, in_discount_id); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateCart(IN in_id varchar(50), IN in_user_id bigint(20), IN in_discount_id bigint(20))
BEGIN
UPDATE tbl_cart SET user_id=in_user_id, discount_id=in_discount_id WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteCart(IN in_user_id bigint(20))
BEGIN
DELETE FROM tbl_cart WHERE user_id=in_user_id;
END$$

CREATE PROCEDURE sp_FindAllCategories()
BEGIN
SELECT * FROM tbl_category;
END$$

CREATE PROCEDURE sp_FindAllCategoriesLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_category LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindCategoryById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_category WHERE id=in_id;
END$$

CREATE PROCEDURE sp_InsertNewCategory(IN in_name varchar(50), IN in_image varchar(255), IN in_description varchar(255))
BEGIN
INSERT INTO tbl_category (name, image, description) VALUES (in_name, in_image, in_description); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateCategory(IN in_id bigint(20), IN in_name varchar(50), IN in_image varchar(255), IN in_description varchar(255))
BEGIN
UPDATE tbl_category SET name=in_name, image=in_image, description=in_description WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteCategory(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_category WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllComments()
BEGIN
SELECT * FROM tbl_comment;
END$$

CREATE PROCEDURE sp_FindAllCommentsLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_comment LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindCommentById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_comment WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindCommentByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_comment WHERE product_id=in_product_id;
END$$

CREATE PROCEDURE sp_FindCommentByUserPhone(IN in_phone bigint(20))
BEGIN
SELECT * FROM tbl_comment WHERE phone=in_phone;
END$$

CREATE PROCEDURE sp_InsertNewComment(IN in_root_comment_id bigint(20), IN in_product_id varchar(50), IN in_username varchar(100), IN in_phone char(15), IN in_content varchar(255))
BEGIN
INSERT INTO tbl_comment (root_comment_id, product_id, username, phone, content) VALUES (in_root_comment_id, in_product_id, in_username, in_phone, in_content); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateComment(IN in_id bigint(20), IN in_root_comment_id bigint(20), IN in_product_id varchar(50), IN in_username varchar(100), IN in_phone char(15), IN in_content varchar(255))
BEGIN
UPDATE tbl_comment SET root_comment_id=in_root_comment_id, product_id=in_product_id, username=in_username, phone=in_phone, content=in_content WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteComment(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_comment WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllDiscounts()
BEGIN
SELECT * FROM tbl_discount;
END$$

CREATE PROCEDURE sp_FindAllDiscountsLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_discount LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindDiscountById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_discount WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindDiscountByType(IN in_applied_type enum('PRODUCT','PURCHASE'))
BEGIN
SELECT * FROM tbl_discount WHERE applied_type=in_applied_type;
END$$

CREATE PROCEDURE sp_InsertNewDiscount(IN in_code varchar(20), IN in_rate float, IN in_applied_type enum('PRODUCT','PURCHASE'), IN in_max_amount bigint(20), IN in_applied_date datetime, IN in_ended_date datetime)
BEGIN
INSERT INTO tbl_discount (code, rate, applied_type, max_amount, applied_date, ended_date) VALUES (in_code, in_rate, in_applied_type, in_max_amount, in_applied_date, in_ended_date); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateDiscount(IN in_id bigint(20), IN in_code varchar(20), IN in_rate float, IN in_applied_type enum('PRODUCT','PURCHASE'), IN in_max_amount bigint(20), IN in_applied_date datetime, IN in_ended_date datetime)
BEGIN
UPDATE tbl_discount SET code=in_code, rate=in_rate, applied_type=in_applied_type, max_amount=in_max_amount, applied_date=in_applied_date, ended_date=in_ended_date WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteDiscount(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_discount WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllFeedbacks()
BEGIN
SELECT * FROM tbl_feedback;
END$$

CREATE PROCEDURE sp_FindAllFeedbacksLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_feedback LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindFeedbackById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_feedback WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindFeedbackByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_feedback WHERE product_id=in_product_id;
END$$

CREATE PROCEDURE sp_FindFeedbackByUserId(IN in_user_id bigint(20))
BEGIN
SELECT * FROM tbl_feedback WHERE user_id=in_user_id;
END$$

CREATE PROCEDURE sp_FindFeedbackByRatingPoint(IN in_rating_point int(11))
BEGIN
SELECT * FROM tbl_feedback WHERE rating_point=in_rating_point;
END$$

CREATE PROCEDURE sp_FindFeedbackByProductIdAndRatingPoint(IN in_product_id varchar(50), IN in_rating_point int(11))
BEGIN
SELECT * FROM tbl_feedback WHERE product_id=in_product_id and rating_point=in_rating_point;
END$$

CREATE PROCEDURE sp_InsertNewFeedback(IN in_product_id varchar(50), IN in_user_id bigint(20), IN in_content varchar(255), IN in_rating_point int(11))
BEGIN
INSERT INTO tbl_feedback (product_id, user_id, content, rating_point) VALUES (in_product_id, in_user_id, in_content, in_rating_point); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateFeedback(IN in_id bigint(20), IN in_product_id varchar(50), IN in_user_id bigint(20), IN in_content varchar(255), IN in_rating_point int(11))
BEGIN
UPDATE tbl_feedback SET content=in_content, rating_point=in_rating_point WHERE id=in_id and product_id=in_product_id and user_id=in_user_id;
END$$

CREATE PROCEDURE sp_DeleteFeedback(IN in_id bigint(20), IN in_product_id varchar(50), IN in_user_id bigint(20))
BEGIN
DELETE FROM tbl_feedback WHERE id=in_id and product_id=in_product_id and user_id=in_user_id;
END$$

CREATE PROCEDURE sp_FindAllImports()
BEGIN
SELECT * FROM tbl_import;
END$$

CREATE PROCEDURE sp_FindAllImportsLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_import LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindImportById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_import WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindImportByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_import WHERE product_id=in_product_id;
END$$

CREATE PROCEDURE sp_InsertNewImport(IN in_id varchar(25), IN in_product_id varchar(50), IN in_quantity bigint(20), IN in_imported_price double, IN in_imported_date datetime)
BEGIN
INSERT INTO tbl_import (id, product_id, quantity, import_price, import_date) VALUES (in_id, in_product_id, in_quantity, in_imported_price, in_imported_date); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateImport(IN in_id varchar(25), IN in_product_id varchar(50), IN in_quantity bigint(20), IN in_imported_price double, IN in_imported_date datetime)
BEGIN
UPDATE tbl_import SET product_id=in_product_id, quantity=in_quantity, import_price=in_imported_price, import_date=in_imported_date WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteImport(IN in_id varchar(25))
BEGIN
DELETE FROM tbl_import WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllInvoices()
BEGIN
SELECT * FROM tbl_invoice;
END$$

CREATE PROCEDURE sp_FindAllInvoicesLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_invoice LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindInvoiceById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_invoice WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindInvoiceByUserId(IN in_user_id bigint(20))
BEGIN
SELECT * FROM tbl_invoice WHERE user_id=in_user_id;
END$$

CREATE PROCEDURE sp_FindInvoiceByPaymentType(IN in_payment_type varchar(50))
BEGIN
SELECT * FROM tbl_invoice WHERE payment_type=in_payment_type;
END$$

CREATE PROCEDURE sp_FindInvoiceByOrderStatus(IN in_order_status enum('PENDING','WAIT_CONFIRMED','PREPARED','SHIPPED','RECEIVED','CANCELED','FAILED'))
BEGIN
SELECT * FROM tbl_invoice WHERE order_status=in_order_status;
END$$

CREATE PROCEDURE sp_FindInvoiceByPaidStatus(IN in_is_paid tinyint(1))
BEGIN
SELECT * FROM tbl_invoice WHERE is_paid=in_is_paid;
END$$

CREATE PROCEDURE sp_InsertNewInvoice(IN in_id varchar(25), IN in_user_id bigint(20), IN in_address varchar(255), IN in_phone char(15), IN in_payment_amount double, IN in_ship_cost double, IN in_discount_amount double, IN in_tax double, IN in_payment_total double, IN in_payment_type varchar(50), IN in_is_paid tinyint(1), IN in_order_status enum('PENDING','WAIT_CONFIRMED','PREPARED','SHIPPED','RECEIVED','CANCELED','FAILED'), IN in_note varchar(255))
BEGIN
INSERT INTO tbl_invoice (id, user_id, address, phone, payment_amount, ship_cost, discount_amount, tax, payment_total, payment_type, is_paid, order_status, note) VALUES (in_id, in_user_id, in_address, in_phone, in_payment_amount, in_ship_cost, in_discount_amount, in_tax, in_payment_total, in_payment_type, in_is_paid, in_order_status, in_note); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateInvoiceStatus(IN in_id varchar(25), IN in_order_status enum('PENDING','WAIT_CONFIRMED','PREPARED','SHIPPED','RECEIVED','CANCELED','FAILED'))
BEGIN
UPDATE tbl_invoice SET order_status=in_order_status WHERE id=in_id;
END$$

CREATE PROCEDURE sp_UpdateInvoicePaymentMethodAndPaidStatus(IN in_id varchar(25), IN in_payment_type varchar(50), IN in_is_paid tinyint(1))
BEGIN
UPDATE tbl_invoice SET payment_type=in_payment_type, is_paid=in_is_paid WHERE id=in_id;
END$$

CREATE PROCEDURE sp_UpdateInvoice(IN in_id varchar(25), IN in_user_id bigint(20), IN in_address varchar(255), IN in_phone char(15), IN in_payment_amount double, IN in_ship_cost double, IN in_discount_amount double, IN in_tax double, IN in_payment_total double, IN in_payment_type varchar(50), IN in_is_paid tinyint(1), IN in_order_status enum('PENDING','WAIT_CONFIRMED','PREPARED','SHIPPED','RECEIVED','CANCELED','FAILED'), IN in_note varchar(255))
BEGIN
UPDATE tbl_invoice SET user_id=in_user_id, address=in_address, phone=in_phone, payment_amount=in_payment_amount, ship_cost=in_ship_cost, discount_amount=in_discount_amount, tax=in_tax, payment_total=in_payment_total, payment_type=in_payment_type, is_paid=in_is_paid, order_status=in_order_status, note=in_note WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteInvoice(IN in_id varchar(25))
BEGIN
DELETE FROM tbl_invoice WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllLabels()
BEGIN
SELECT * FROM tbl_label;
END$$

CREATE PROCEDURE sp_FindAllLabelsLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_label LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindLabelById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_label WHERE id=in_id;
END$$

CREATE PROCEDURE sp_InsertNewLabel(IN in_name varchar(25), IN in_icon varchar(255), IN in_title varchar(50), IN in_description varchar(255))
BEGIN
INSERT INTO tbl_label (name, icon, title, description) VALUES (in_name, in_icon, in_title, in_description); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateLabel(IN in_id bigint(20), IN in_name varchar(25), IN in_icon varchar(255), IN in_title varchar(50), IN in_description varchar(255))
BEGIN
UPDATE tbl_label SET name=in_name, icon=in_icon, title=in_title, description=in_description WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteLabel(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_label WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllProducts()
BEGIN
SELECT * FROM tbl_product;
END$$

CREATE PROCEDURE sp_FindProductById(IN in_id varchar(50))
BEGIN
SELECT * FROM tbl_product WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindProductByBrandId(IN in_brand_id bigint(20))
BEGIN
SELECT * FROM tbl_product WHERE brand_id=in_brand_id;
END$$

CREATE PROCEDURE sp_FindProductByCategoryId(IN in_category_id bigint(20))
BEGIN
SELECT * FROM tbl_product WHERE category_id=in_category_id;
END$$

CREATE PROCEDURE sp_InsertNewProduct(IN in_id varchar(50), IN in_brand_id bigint(20), IN in_category_id bigint(20), IN in_name varchar(100), IN in_released_date date, IN in_quantity_in_stock int(11), IN in_listed_price double, IN in_specifications text, IN in_description_detail text)
BEGIN
INSERT INTO tbl_product (id, brand_id, category_id, name, released_date, quantity_in_stock, listed_price, specifications, description_detail) VALUES (in_id, in_brand_id, in_category_id, in_name, in_released_date, in_quantity_in_stock, in_listed_price, in_specifications, in_description_detail); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateProduct(IN in_id varchar(50), IN in_brand_id bigint(20), IN in_category_id bigint(20), IN in_name varchar(100), IN in_released_date date, IN in_quantity_in_stock int(11), IN in_listed_price double, IN in_specifications text, IN in_description_detail text)
BEGIN
UPDATE tbl_product SET brand_id=in_brand_id, category_id=in_category_id, name=in_name, released_date=in_released_date, quantity_in_stock=in_quantity_in_stock, listed_price=in_listed_price, specifications=in_specifications, description_detail=in_description_detail WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteProduct(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_product WHERE id=in_id;
END$$

CREATE PROCEDURE sp_InsertProductAccessory(IN in_product_id varchar(255), IN in_accessory_id bigint(20))
BEGIN
INSERT INTO tbl_product_accessory (product_id, accessory_id) VALUES (in_product_id, in_accessory_id); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_DeleteProductAccessory(IN in_product_id varchar(255), IN in_accessory_id bigint(20))
BEGIN
DELETE FROM tbl_product_accessory WHERE product_id=in_product_id and accessory_id=in_accessory_id;
END$$

CREATE PROCEDURE sp_InsertProductDiscount(IN in_product_id varchar(255), IN in_discount_id bigint(20))
BEGIN
INSERT INTO tbl_product_discount (product_id, discount_id) VALUES (in_product_id, in_discount_id); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_DeleteProductDiscount(IN in_product_id varchar(255), IN in_discount_id bigint(20))
BEGIN
DELETE FROM tbl_product_discount WHERE product_id=in_product_id and discount_id=in_discount_id;
END$$

CREATE PROCEDURE sp_InsertProductLabel(IN in_product_id varchar(255), IN in_label_id bigint(20))
BEGIN
INSERT INTO tbl_product_label (product_id, label_id) VALUES (in_product_id, in_label_id); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_DeleteProductLabel(IN in_product_id varchar(255), IN in_label_id bigint(20))
BEGIN
DELETE FROM tbl_product_label WHERE product_id=in_product_id and label_id=in_label_id;
END$$

CREATE PROCEDURE sp_FindAllProductImages()
BEGIN
SELECT * FROM tbl_product_image;
END$$

CREATE PROCEDURE sp_FindProductImageById(IN in_id varchar(50))
BEGIN
SELECT * FROM tbl_product_image WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindProductImageByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_product_image WHERE product_id=in_product_id;
END$$

CREATE PROCEDURE sp_FindProductImageByImageType(IN in_type enum('ADVERTISE','DETAIL','ADDITIONAL','FEEDBACK'))
BEGIN
SELECT * FROM tbl_product_image WHERE type=in_type;
END$$

CREATE PROCEDURE sp_FindProductImageByProductIdAndImageType(IN in_product_id varchar(50), IN in_type enum('ADVERTISE','DETAIL','ADDITIONAL','FEEDBACK'))
BEGIN
SELECT * FROM tbl_product_image WHERE product_id=in_product_id and type=in_type;
END$$

CREATE PROCEDURE sp_InsertNewProductImage(IN in_id varchar(50), IN in_product_id varchar(50), IN in_feedback_id varchar(25), IN in_url varchar(255), IN in_type enum('ADVERTISE','DETAIL','ADDITIONAL','FEEDBACK'))
BEGIN
INSERT INTO tbl_product_image (id, product_id, feedback_id, url, type) VALUES (in_id, in_product_id, in_feedback_id, in_url, in_type); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateProductImage(IN in_id varchar(50), IN in_product_id varchar(50), IN in_feedback_id varchar(25), IN in_url varchar(255), IN in_type enum('ADVERTISE','DETAIL','ADDITIONAL','FEEDBACK'))
BEGIN
UPDATE tbl_product_image SET product_id=in_product_id, feedback_id=in_feedback_id, url=in_url, type=in_type WHERE id=in_id;
END$$

CREATE PROCEDURE sp_UpdateProductImagePathAndType(IN in_id varchar(50), IN in_url varchar(255), IN in_type enum('ADVERTISE','DETAIL','ADDITIONAL','FEEDBACK'))
BEGIN
UPDATE tbl_product_image SET url=in_url, type=in_type WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteProductImage(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_product_image WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllProductUnits()
BEGIN
SELECT * FROM tbl_product_unit;
END$$

CREATE PROCEDURE sp_FindProductUnitById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_product_unit WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindProductUnitByCartId(IN in_cart_id varchar(25))
BEGIN
SELECT * FROM tbl_product_unit WHERE cart_id=in_cart_id;
END$$

CREATE PROCEDURE sp_FindProductUnitByInvoiceId(IN in_invoice_id varchar(25))
BEGIN
SELECT * FROM tbl_product_unit WHERE invoice_id=in_invoice_id;
END$$

CREATE PROCEDURE sp_InsertNewProductUnit(IN in_product_id varchar(50), IN in_cart_id varchar(25), IN in_invoice_id varchar(25), IN in_quantity int(11), IN in_price double, IN in_discount_price double)
BEGIN
INSERT INTO tbl_product_unit (product_id, cart_id, invoice_id, quantity, price, discount_price) VALUES (in_product_id, in_cart_id, in_invoice_id, in_quantity, in_price, in_discount_price); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateProductUnitProperties(IN in_id varchar(25), IN in_quantity int(11), IN in_price double, IN in_discount_price double)
BEGIN
UPDATE tbl_product_unit SET quantity=in_quantity, price=in_price, discount_price=in_discount_price WHERE id=in_id;
END$$

CREATE PROCEDURE sp_UpdateProductUnit(IN in_id varchar(25), IN in_product_id varchar(50), IN in_cart_id varchar(25), IN in_invoice_id varchar(25), IN in_quantity int(11), IN in_price double, IN in_discount_price double)
BEGIN
UPDATE tbl_product_unit SET product_id=in_product_id, cart_id=in_cart_id, invoice_id=in_invoice_id, quantity=in_quantity, price=in_price, discount_price=in_discount_price WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteProductUnit(IN in_id varchar(25))
BEGIN
DELETE FROM tbl_product_unit WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindAllRoles()
BEGIN
SELECT * FROM tbl_role;
END$$

CREATE PROCEDURE sp_FindAllRolesLimit(IN in_limit bigint(20), IN in_offset bigint(20))
BEGIN
SELECT * FROM tbl_role LIMIT in_limit OFFSET in_offset;
END$$

CREATE PROCEDURE sp_FindRoleById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_role WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindRoleByName(IN in_name varchar(25))
BEGIN
SELECT * FROM tbl_role WHERE name=in_name;
END$$

CREATE PROCEDURE sp_InsertNewRole(IN in_name varchar(25), IN in_description varchar(100))
BEGIN
INSERT INTO tbl_role (name, description) VALUES (in_name, in_description); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateRole(IN in_id int(11), IN in_name varchar(25), IN in_description varchar(100))
BEGIN
UPDATE tbl_role SET name=in_name, description=in_description WHERE id=in_id;
END$$

CREATE PROCEDURE sp_DeleteRole(IN in_id bigint(20))
BEGIN
DELETE FROM tbl_role WHERE id=in_id;
END$$

CREATE PROCEDURE sp_InsertUserRole(IN in_user_id varchar(255), IN in_role_id bigint(20))
BEGIN
INSERT INTO tbl_user_role (user_id, role_id) VALUES (in_user_id, in_role_id); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_DeleteUserRole(IN in_user_id varchar(255), IN in_role_id bigint(20))
BEGIN
DELETE FROM tbl_user_role WHERE user_id=in_user_id and role_id=in_role_id;
END$$

CREATE PROCEDURE sp_FindAllUsers()
BEGIN
SELECT * FROM tbl_user;
END$$

CREATE PROCEDURE sp_FindUserById(IN in_id bigint(20))
BEGIN
SELECT * FROM tbl_user WHERE id=in_id;
END$$

CREATE PROCEDURE sp_FindUserByPhone(IN in_phone char(15))
BEGIN
SELECT * FROM tbl_user WHERE phone=in_phone;
END$$

CREATE PROCEDURE sp_FindUserByGender(IN in_gender enum('MALE', 'FEMALE', 'OTHER'))
BEGIN
SELECT * FROM tbl_user WHERE gender=in_gender;
END$$

CREATE PROCEDURE sp_InsertNewUser(IN in_name varchar(100), IN in_gender enum('MALE', 'FEMALE', 'OTHER'), IN in_date_of_birth date, IN in_phone char(15), IN in_email varchar(50), IN in_password varchar(100), IN in_is_active tinyint(1))
BEGIN
INSERT INTO tbl_user (name, gender, date_of_birth, phone, email, password, is_active) VALUES (in_name, in_gender, in_date_of_birth, in_phone, in_email, in_password, in_is_active); SELECT LAST_INSERT_ID();
END$$

CREATE PROCEDURE sp_UpdateUser(IN in_id bigint(20), IN in_name varchar(100), IN in_gender enum('MALE', 'FEMALE', 'OTHER'), IN in_date_of_birth date, IN in_phone char(15), IN in_email varchar(50), IN in_password varchar(100))
BEGIN
UPDATE tbl_user SET name=in_name, gender=in_gender, date_of_birth=in_date_of_birth, phone=in_phone, email=in_email, password=in_password WHERE id=in_id;
END$$

CREATE PROCEDURE sp_UpdateUserInformation(IN in_id bigint(20), IN in_name varchar(100), IN in_gender enum('MALE', 'FEMALE', 'OTHER'), IN in_date_of_birth date, IN in_email varchar(50))
BEGIN
UPDATE tbl_user SET name=in_name, gender=in_gender, date_of_birth=in_date_of_birth, email=in_email WHERE id=in_id;
END$$

CREATE PROCEDURE sp_UpdateUserAccountStatus(IN in_id bigint(20), IN in_is_active tinyint(1))
BEGIN
UPDATE tbl_user SET is_active=in_is_active WHERE id=in_id;
END$$
