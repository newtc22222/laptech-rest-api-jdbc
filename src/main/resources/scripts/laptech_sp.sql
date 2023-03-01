create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistAddress(IN in_user_id bigint(11), IN in_country varchar(100),
                                                                   IN in_line1 varchar(100), IN in_line2 varchar(100),
                                                                   IN in_line3 varchar(100), IN in_street varchar(100))
BEGIN
	select * from tbl_address 
    where user_id=in_user_id and country=in_country and line1=in_line1 and line2=in_line2 and line3=in_line3 and street=in_street;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistBanner(IN in_path varchar(255), IN in_type varchar(100),
                                                                  IN in_title varchar(100),
                                                                  IN in_link_product varchar(255), IN in_used_date date,
                                                                  IN in_ended_date date)
BEGIN
	select * from tbl_banner
	where path=in_path and type=in_type and title=in_title and link_product=in_link_product and used_date=in_used_date and ended_date=in_ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistBrand(IN in_name varchar(50), IN in_country varchar(50),
                                                                 IN in_establish_date date, IN in_logo varchar(255))
BEGIN
	select * from tbl_brand
	where name=in_name and country=in_country and establish_date=in_establish_date and logo=in_logo;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistCart(IN in_id varchar(25), IN in_user_id bigint(11))
BEGIN
	select * from tbl_cart
	where id=in_id and user_id=in_user_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistCategory(IN in_name varchar(50), IN in_image varchar(255),
                                                                    IN in_description varchar(255))
BEGIN
	select * from tbl_category
	where name=in_name and image=in_image and description=in_description;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistComment(IN in_root_comment_id varchar(25),
                                                                   IN in_product_id varchar(50),
                                                                   IN in_username varchar(100), IN in_phone char(15),
                                                                   IN in_content varchar(255))
BEGIN
	select * from tbl_comment
	where root_comment_id=in_root_comment_id and product_id=in_product_id and username=in_username and phone=in_phone and content=in_content;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistDiscount(IN in_code varchar(20), IN in_rate float,
                                                                    IN in_applied_type enum ('PRODUCT', 'PURCHASE'),
                                                                    IN in_max_amount decimal(21, 4),
                                                                    IN in_applied_date datetime,
                                                                    IN in_ended_date datetime)
BEGIN
	select * from tbl_discount
	where code=in_code and rate=in_rate and applied_type=in_applied_type and max_amount=in_max_amount and applied_date=in_applied_date and ended_date=in_ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistFeedback(IN in_content varchar(255),
                                                                    IN in_rating_point tinyint(3),
                                                                    IN in_product_id varchar(50), IN in_user_id bigint)
BEGIN
	select * from tbl_feedback
	where content=in_content and rating_point=in_rating_point and product_id=in_product_id and user_id=in_user_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistImport(IN in_product_id varchar(50), IN in_quantity bigint,
                                                                  IN in_imported_price decimal(21, 4),
                                                                  IN in_imported_date datetime)
BEGIN
	select * from tbl_import
	where product_id=in_product_id and quantity=in_quantity and imported_date=in_imported_price and imported_price=in_imported_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistInvoice(IN in_user_id bigint, IN in_address varchar(255),
                                                                   IN in_phone char(15),
                                                                   IN in_payment_amount decimal(21, 4),
                                                                   IN in_ship_cost decimal(21, 4),
                                                                   IN in_discount_amount decimal(21, 4),
                                                                   IN in_tax decimal(21, 4),
                                                                   IN in_payment_total decimal(21, 4),
                                                                   IN in_payment_type varchar(50),
                                                                   IN in_is_paid tinyint(1),
                                                                   IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED', 'IGNORED'),
                                                                   IN in_note varchar(255))
BEGIN
select * from tbl_invoice
where user_id=in_user_id and address=in_address and phone=in_phone and payment_amount=in_payment_amount and ship_cost=in_ship_cost and discount_amount=in_discount_amount and tax=in_tax 
and payment_total=in_payment_total and payment_type=in_payment_type and is_paid=in_is_paid and order_status=in_order_status and note=in_note;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistLabel(IN in_name varchar(25), IN in_icon varchar(255),
                                                                 IN in_title varchar(50), IN in_description varchar(255))
BEGIN
select * from tbl_label
where name=in_name and icon=in_icon and title=in_title and description=in_description;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistProduct(IN in_brand_id bigint, IN in_category_id bigint,
                                                                   IN in_name varchar(100), IN in_released_date date,
                                                                   IN in_quantity_in_stock int,
                                                                   IN in_listed_price decimal(21, 4))
BEGIN
select * from tbl_product
where brand_id=in_brand_id and category_id=in_category_id and name=in_name and released_date=in_released_date and quantity_in_stock=in_quantity_in_stock and listed_price=in_listed_price;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistProductImage(IN in_product_id varchar(50),
                                                                        IN in_feedback_id bigint,
                                                                        IN in_url varchar(255),
                                                                        IN in_type enum ('ADVERTISE', 'DETAIL', 'EXTRA', 'FEEDBACK'))
BEGIN
select * from tbl_product_image
where product_id=in_product_id and feedback_id=in_feedback_id and url=in_url and type=in_type;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistProductUnit(IN in_cart_id varchar(50),
                                                                       IN in_invoice_id varchar(50),
                                                                       IN in_product_id varchar(50), IN in_quantity int,
                                                                       IN in_price decimal(21, 4),
                                                                       IN in_discount_price decimal(21, 4))
BEGIN
select * from tbl_product_unit
where cart_id=in_cart_id and invoice_id=in_invoice_id and product_id=in_product_id and quantity=in_quantity and price=in_price and discount_price=in_discount_price;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistRole(IN in_name varchar(25), IN in_description varchar(100))
BEGIN
select * from tbl_role
where name=in_name and description=in_description;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CheckExistUser(IN in_name varchar(100),
                                                                IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),
                                                                IN in_date_of_birth date, IN in_phone char(15),
                                                                IN in_email varchar(100))
BEGIN
select * from tbl_user
where name=in_name and gender=in_gender and date_of_birth=in_date_of_birth and phone=in_phone and email=in_email;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAddressWithCondition(IN in_user_id bigint,
                                                                           IN in_country varchar(25),
                                                                           IN in_line1 varchar(25),
                                                                           IN in_line2 varchar(25),
                                                                           IN in_line3 varchar(25),
                                                                           IN in_street varchar(100),
                                                                           IN in_is_default tinyint(1),
                                                                           IN in_created_date date,
                                                                           IN in_modified_date date,
                                                                           IN in_deleted_date date,
                                                                           IN in_is_del tinyint(1),
                                                                           IN in_update_by varchar(100))
BEGIN
set in_country = concat('%', in_country, '%');
set in_line1 = concat('%', in_line1, '%');
set in_line2 = concat('%', in_line2, '%');
set in_line3 = concat('%', in_line3, '%');
set in_street = concat('%', in_street, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_address WHERE ((in_user_id=null) or (user_id=in_user_id))
and ((in_country=null) or (country like in_country))
and ((in_line1=null) or (line1 like in_line1))
and ((in_line2=null) or (line2 like in_line2))
and ((in_line3=null) or (line3 like in_line3))
and ((in_street=null) or (street like in_street))
and ((in_is_default=null) or (is_default=in_is_default))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllAddress()
BEGIN
SELECT COUNT(*) FROM tbl_address;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllBanner()
BEGIN
SELECT COUNT(*) FROM tbl_banner;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllBrand()
BEGIN
SELECT COUNT(*) FROM tbl_brand;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllCategory()
BEGIN
SELECT COUNT(*) FROM tbl_category;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllComment()
BEGIN
SELECT COUNT(*) FROM tbl_comment;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllDiscount()
BEGIN
SELECT COUNT(*) FROM tbl_discount;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllFeedback()
BEGIN
SELECT COUNT(*) FROM tbl_feedback;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllImport()
BEGIN
SELECT COUNT(*) FROM tbl_import;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllInvoice()
BEGIN
SELECT COUNT(*) FROM tbl_invoice;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllLabel()
BEGIN
SELECT COUNT(*) FROM tbl_label;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllLogSystem()
BEGIN
  SELECT COUNT(*) FROM tbl_log_system;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllProduct()
BEGIN
SELECT COUNT(*) FROM tbl_product;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllProductImage()
BEGIN
SELECT COUNT(*) FROM tbl_product_image;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllProductUnit()
BEGIN
SELECT COUNT(*) FROM tbl_product_unit;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllRefreshToken()
BEGIN
  SELECT COUNT(*) FROM tbl_refresh_token;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllRole()
BEGIN
SELECT COUNT(*) FROM tbl_role;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountAllUser()
BEGIN
SELECT COUNT(*) FROM tbl_user;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountBannerWithCondition(IN in_path varchar(255),
                                                                          IN in_type varchar(50),
                                                                          IN in_title varchar(100),
                                                                          IN in_link_product varchar(255),
                                                                          IN in_used_date date, IN in_ended_date date,
                                                                          IN in_created_date date,
                                                                          IN in_modified_date date,
                                                                          IN in_deleted_date date,
                                                                          IN in_is_del tinyint(1),
                                                                          IN in_update_by varchar(100))
BEGIN
set in_path = concat('%', in_path, '%');
set in_type = concat('%', in_type, '%');
set in_title = concat('%', in_title, '%');
set in_link_product = concat('%', in_link_product, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_banner WHERE ((in_path=null) or (path like in_path))
and ((in_type=null) or (type like in_type))
and ((in_title=null) or (title like in_title))
and ((in_link_product=null) or (link_product like in_link_product))
and ((in_used_date=null) or (used_date=in_used_date))
and ((in_ended_date=null) or (ended_date=in_ended_date))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountBrandWithCondition(IN in_name varchar(100),
                                                                         IN in_country varchar(100),
                                                                         IN in_establish_year year,
                                                                         IN in_logo varchar(255),
                                                                         IN in_created_date date,
                                                                         IN in_modified_date date,
                                                                         IN in_deleted_date date,
                                                                         IN in_is_del tinyint(1),
                                                                         IN in_update_by varchar(100))
BEGIN
set in_name = concat('%', in_name, '%');
set in_country = concat('%', in_country, '%');
set in_logo = concat('%', in_logo, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_brand WHERE ((in_name=null) or (name like in_name))
and ((in_country=null) or (country like in_country))
and ((in_establish_year=null) or (year(establish_date)=in_establish_year))
and ((in_logo=null) or (logo like in_logo))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountCategoryWithCondition(IN in_name varchar(50),
                                                                            IN in_image varchar(255),
                                                                            IN in_description varchar(255),
                                                                            IN in_created_date date,
                                                                            IN in_modified_date date,
                                                                            IN in_deleted_date date,
                                                                            IN in_is_del tinyint(1),
                                                                            IN in_update_by varchar(100))
BEGIN
set in_name = concat('%', in_name, '%');
set in_image = concat('%', in_image, '%');
set in_description = concat('%', in_description, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_category WHERE ((in_name=null) or (name like in_name))
and ((in_image=null) or (image like in_image))
and ((in_description=null) or (description like in_description))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountCommentWithCondition(IN in_root_comment_id varchar(25),
                                                                           IN in_product_id varchar(50),
                                                                           IN in_username varchar(100),
                                                                           IN in_phone char(15),
                                                                           IN in_content varchar(255),
                                                                           IN in_created_date date,
                                                                           IN in_modified_date date,
                                                                           IN in_deleted_date date,
                                                                           IN in_is_del tinyint(1),
                                                                           IN in_update_by varchar(100))
BEGIN
set in_username = concat('%', in_username, '%');
set in_content = concat('%', in_content, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_comment WHERE ((in_root_comment_id=null) or (root_comment_id=in_root_comment_id))
and ((in_product_id=null) or (product_id=in_product_id))
and ((in_username=null) or (username like in_username))
and ((in_phone=null) or (phone=in_phone))
and ((in_content=null) or (content like in_content))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountDiscountWithCondition(IN in_code varchar(20), IN in_rate float,
                                                                            IN in_max_amount decimal(19, 4),
                                                                            IN in_applied_type enum ('PRODUCT', 'PURCHASE'),
                                                                            IN in_applied_date datetime,
                                                                            IN in_ended_date datetime,
                                                                            IN in_created_date date,
                                                                            IN in_modified_date date,
                                                                            IN in_deleted_date date,
                                                                            IN in_is_del tinyint(1),
                                                                            IN in_update_by varchar(100))
BEGIN
set in_code = concat('%', in_code, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_discount WHERE ((in_code=null) or (code like in_code))
and ((in_rate=null) or (rate=in_rate))
and ((in_max_amount=null) or (max_amount=in_max_amount))
and ((in_applied_type=null) or (applied_type=in_applied_type))
and ((in_applied_date=null) or (applied_date=in_applied_date))
and ((in_ended_date=null) or (ended_date=in_ended_date))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountFeedbackWithCondition(IN in_product_id varchar(50),
                                                                            IN in_user_id bigint,
                                                                            IN in_content varchar(255),
                                                                            IN in_rating_point int,
                                                                            IN in_created_date date,
                                                                            IN in_modified_date date,
                                                                            IN in_deleted_date date,
                                                                            IN in_is_del tinyint(1),
                                                                            IN in_update_by varchar(100))
BEGIN
set in_content = concat('%', in_content, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_feedback WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_user_id=null) or (user_id=in_user_id))
and ((in_content=null) or (content like in_content))
and ((in_rating_point=null) or (rating_point=in_rating_point))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountImportWithCondition(IN in_product_id varchar(50),
                                                                          IN in_quantity bigint,
                                                                          IN in_imported_price decimal(21, 4),
                                                                          IN in_imported_date date,
                                                                          IN in_created_date date,
                                                                          IN in_modified_date date,
                                                                          IN in_deleted_date date,
                                                                          IN in_is_del tinyint(1),
                                                                          IN in_update_by varchar(100))
BEGIN
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_import WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_quantity=null) or (quantity=in_quantity))
and ((in_imported_price=null) or (imported_price=in_imported_price))
and ((in_imported_date=null) or (cast(imported_date as date)=in_imported_date))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountInvoiceWithCondition(IN in_user_id bigint,
                                                                           IN in_address varchar(255),
                                                                           IN in_phone char(15),
                                                                           IN in_payment_amount decimal(21, 4),
                                                                           IN in_ship_cost decimal(21, 4),
                                                                           IN in_discount_amount decimal(21, 4),
                                                                           IN in_tax decimal(21, 4),
                                                                           IN in_payment_total decimal(21, 4),
                                                                           IN in_payment_type varchar(50),
                                                                           IN in_is_paid tinyint(1),
                                                                           IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),
                                                                           IN in_note varchar(255),
                                                                           IN in_created_date date,
                                                                           IN in_modified_date date,
                                                                           IN in_deleted_date date,
                                                                           IN in_is_del tinyint(1),
                                                                           IN in_update_by varchar(100))
BEGIN
set in_address = concat('%', in_address, '%');
set in_payment_type = concat('%', in_payment_type, '%');
set in_note = concat('%', in_note, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_invoice WHERE ((in_user_id=null) or (user_id=in_user_id))
and ((in_address=null) or (address like in_address))
and ((in_phone=null) or (phone=in_phone))
and ((in_payment_amount=null) or (payment_amount=in_payment_amount))
and ((in_ship_cost=null) or (ship_cost=in_ship_cost))
and ((in_discount_amount=null) or (discount_amount=in_discount_amount))
and ((in_tax=null) or (tax=in_tax))
and ((in_payment_total=null) or (payment_total=in_payment_total))
and ((in_payment_type=null) or (payment_type like in_payment_type))
and ((in_is_paid=null) or (is_paid=in_is_paid))
and ((in_order_status=null) or (order_status=in_order_status))
and ((in_note=null) or (note like in_note))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountLabelWithCondition(IN in_name varchar(25),
                                                                         IN in_icon varchar(255),
                                                                         IN in_title varchar(50),
                                                                         IN in_description varchar(255),
                                                                         IN in_created_date date,
                                                                         IN in_modified_date date,
                                                                         IN in_deleted_date date,
                                                                         IN in_is_del tinyint(1),
                                                                         IN in_update_by varchar(100))
BEGIN
set in_name = concat('%', in_name, '%');
set in_icon = concat('%', in_icon, '%');
set in_title = concat('%', in_title, '%');
set in_description = concat('%', in_description, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_label WHERE ((in_name=null) or (name like in_name))
and ((in_icon=null) or (icon like in_icon))
and ((in_title=null) or (title like in_title))
and ((in_description=null) or (description like in_description))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountLogSystemWithCondition(IN in_action_table varchar(50),
                                                                             IN in_action_date date,
                                                                             IN in_action_by varchar(100),
                                                                             IN in_action_name varchar(25))
BEGIN
  set in_action_table = concat('%', in_action_table, '%');
  set in_action_by = concat('%', in_action_by, '%');
  set in_action_name = concat('%', in_action_name, '%');

  SELECT COUNT(*) FROM tbl_log_system 
  WHERE ((in_action_table=null) or (action_table like in_action_table))
  and ((in_action_date=null) or (cast(action_time as date) = in_action_date))
  and ((in_action_by=null) or (action_by like in_action_by))
  and ((in_action_name=null) or (action_name like in_action_name));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountProductImageWithCondition(IN in_product_id varchar(50),
                                                                                IN in_feedback_id varchar(25),
                                                                                IN in_url varchar(255),
                                                                                IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),
                                                                                IN in_created_date date,
                                                                                IN in_modified_date date,
                                                                                IN in_deleted_date date,
                                                                                IN in_is_del tinyint(1),
                                                                                IN in_update_by varchar(100))
BEGIN
set in_url = concat('%', in_url, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_product_image WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_feedback_id=null) or (feedback_id=in_feedback_id))
and ((in_url=null) or (url like in_url))
and ((in_type=null) or (type=in_type))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountProductUnitWithCondition(IN in_product_id varchar(50),
                                                                               IN in_cart_id varchar(50),
                                                                               IN in_invoice_id varchar(50),
                                                                               IN in_quantity int,
                                                                               IN in_price decimal(21, 4),
                                                                               IN in_discount_price decimal(21, 4),
                                                                               IN in_created_date date,
                                                                               IN in_modified_date date,
                                                                               IN in_deleted_date date,
                                                                               IN in_is_del tinyint(1),
                                                                               IN in_update_by varchar(100))
BEGIN
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_product_unit WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_cart_id=null) or (cart_id=in_cart_id))
and ((in_invoice_id=null) or (invoice_id=in_invoice_id))
and ((in_quantity=null) or (quantity=in_quantity))
and ((in_price=null) or (price=in_price))
and ((in_discount_price=null) or (discount_price=in_discount_price))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountProductWithCondition(IN in_brand_id bigint,
                                                                           IN in_category_id bigint,
                                                                           IN in_name varchar(100),
                                                                           IN in_released_date date,
                                                                           IN in_quantity_in_stock int,
                                                                           IN in_listed_price decimal(21, 4),
                                                                           IN in_created_date date,
                                                                           IN in_modified_date date,
                                                                           IN in_deleted_date date,
                                                                           IN in_is_del tinyint(1),
                                                                           IN in_update_by varchar(100))
BEGIN
set in_name = concat('%', in_name, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_product WHERE ((in_brand_id=null) or (brand_id=in_brand_id))
and ((in_category_id=null) or (category_id=in_category_id))
and ((in_name=null) or (name like in_name))
and ((in_released_date=null) or (released_date=in_released_date))
and ((in_quantity_in_stock=null) or (quantity_in_stock=in_quantity_in_stock))
and ((in_listed_price=null) or (listed_price=in_listed_price))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountRefreshTokenInDate(IN in_date date)
BEGIN
  SELECT COUNT(*) FROM tbl_refresh_token WHERE CAST(created_date AS date) = in_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountRoleWithCondition(IN in_name varchar(25),
                                                                        IN in_description varchar(100),
                                                                        IN in_created_date datetime,
                                                                        IN in_modified_date datetime,
                                                                        IN in_deleted_date datetime,
                                                                        IN in_is_del tinyint(1),
                                                                        IN in_update_by varchar(100))
BEGIN
set in_name = concat('%', in_name, '%');
set in_description = concat('%', in_description, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_role WHERE ((in_name=null) or (name like in_name))
and ((in_description=null) or (description like in_description))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_CountUserWithCondition(IN in_name varchar(100),
                                                                        IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),
                                                                        IN in_date_of_birth date, IN in_phone char(15),
                                                                        IN in_email varchar(50),
                                                                        IN in_is_active tinyint(1),
                                                                        IN in_created_date date,
                                                                        IN in_modified_date date,
                                                                        IN in_deleted_date date,
                                                                        IN in_is_del tinyint(1),
                                                                        IN in_update_by varchar(100))
BEGIN
set in_name = concat('%', in_name, '%');
set in_email = concat('%', in_email, '%');
set in_update_by = concat('%', in_update_by, '%'); 

SELECT COUNT(*) FROM tbl_user WHERE ((in_name=null) or (name like in_name))
and ((in_gender=null) or (gender=in_gender))
and ((in_date_of_birth=null) or (date_of_birth=in_date_of_birth))
and ((in_phone=null) or (phone=in_phone))
and ((in_email=null) or (email like in_email))
and ((in_is_active=null) or (is_active=in_is_active))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by));
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteAddress(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_address SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteBanner(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_banner SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteBrand(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_brand SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteCart(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_cart SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteCategory(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_category SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteComment(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_comment SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteDiscount(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_discount SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteFeedback(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_feedback SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteImport(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_import SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteInvoice(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_invoice SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteLabel(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_label SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteProduct(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteProductAccessory(IN in_product_id varchar(255), IN in_accessory_id bigint)
BEGIN
DELETE FROM tbl_product_accessory WHERE product_id=in_product_id and accessory_id=in_accessory_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteProductDiscount(IN in_product_id varchar(255), IN in_discount_id bigint)
BEGIN
DELETE FROM tbl_product_discount WHERE product_id=in_product_id and discount_id=in_discount_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteProductImage(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product_image SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteProductLabel(IN in_product_id varchar(255), IN in_label_id bigint)
BEGIN
DELETE FROM tbl_product_label WHERE product_id=in_product_id and label_id=in_label_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteProductUnit(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product_unit SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteRole(IN in_id int, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_role SET is_del=true and update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteUser(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_user SET is_del=true and update_by=in_update_by WHERE id=in_id and is_active=0;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_DeleteUserRole(IN in_user_id bigint, IN in_role_id bigint)
BEGIN
DELETE FROM tbl_user_role WHERE user_id=in_user_id and role_id=in_role_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAccessoryByProductId(IN in_product_id varchar(50))
BEGIN
select * from tbl_product where id in (
  select accessory_id from tbl_product_accessory where product_id = in_product_id
);
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAddressById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_address WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAddressByUserId(IN in_user_id bigint)
BEGIN
SELECT * FROM tbl_address WHERE user_id=in_user_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllAddress(IN in_sort_by varchar(25),
                                                                IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_address where is_del=false;
  end if;
  
  SELECT * FROM tbl_address 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'user_id' THEN user_id
				WHEN 'country' THEN country
				WHEN 'line1' THEN line1
				WHEN 'line2' THEN line2
				WHEN 'line3' THEN line3
				WHEN 'street' THEN street
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'user_id' THEN user_id
				WHEN 'country' THEN country
				WHEN 'line1' THEN line1
				WHEN 'line2' THEN line2
				WHEN 'line3' THEN line3
				WHEN 'street' THEN street
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllBanners(IN in_sort_by varchar(25),
                                                                IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_banner where is_del=false;
  end if;
  
  SELECT * FROM tbl_banner 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'path' THEN path
				WHEN 'type' THEN type
				WHEN 'title' THEN title
				WHEN 'link_product' THEN link_product
				WHEN 'used_date' THEN used_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'path' THEN path
				WHEN 'type' THEN type
				WHEN 'title' THEN title
				WHEN 'link_product' THEN link_product
				WHEN 'used_date' THEN used_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllBrands(IN in_sort_by varchar(25),
                                                               IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                               IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_brand where is_del=false;
  end if;
  
  SELECT * FROM tbl_brand 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'country' THEN country
				WHEN 'establish_date' THEN establish_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'country' THEN country
				WHEN 'establish_date' THEN establish_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllCategories(IN in_sort_by varchar(25),
                                                                   IN in_sort_dir enum ('ASC', 'DESC'),
                                                                   IN in_offset int, IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_category where is_del=false;
  end if;
  
  SELECT * FROM tbl_category 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllComments(IN in_sort_by varchar(25),
                                                                 IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                 IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_comment where is_del=false;
  end if;
  
  SELECT * FROM tbl_comment 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'root_comment_id' THEN root_comment_id
				WHEN 'product_id' THEN product_id
				WHEN 'username' THEN username
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'root_comment_id' THEN root_comment_id
				WHEN 'product_id' THEN product_id
				WHEN 'username' THEN username
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllDiscounts(IN in_sort_by varchar(25),
                                                                  IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                  IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_discount where is_del=false;
  end if;
  
  SELECT * FROM tbl_discount 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'code' THEN code
				WHEN 'rate' THEN rate
				WHEN 'max_amount' THEN max_amount
				WHEN 'applied_type' THEN applied_type
				WHEN 'applied_date' THEN applied_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'code' THEN code
				WHEN 'rate' THEN rate
				WHEN 'max_amount' THEN max_amount
				WHEN 'applied_type' THEN applied_type
				WHEN 'applied_date' THEN applied_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllFeedbacks(IN in_sort_by varchar(25),
                                                                  IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                  IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_feedback where is_del=false;
  end if;
  
  SELECT * FROM tbl_feedback 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'user_id' THEN user_id
				WHEN 'rating_point' THEN rating_point
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'user_id' THEN user_id
				WHEN 'rating_point' THEN rating_point
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllImports(IN in_sort_by varchar(25),
                                                                IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_import where is_del=false;
  end if;
  
  SELECT * FROM tbl_import 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'quantity' THEN quantity
				WHEN 'imported_price' THEN imported_price
				WHEN 'imported_date' THEN imported_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'quantity' THEN quantity
				WHEN 'imported_price' THEN imported_price
				WHEN 'imported_date' THEN imported_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllInvoices(IN in_sort_by varchar(25),
                                                                 IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                 IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_invoice where is_del=false;
  end if;
  
  SELECT * FROM tbl_invoice 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'user_id' THEN user_id
				WHEN 'address' THEN address
				WHEN 'phone' THEN phone
				WHEN 'payment_amount' THEN payment_amount
				WHEN 'ship_cost' THEN ship_cost
				WHEN 'discount_amount' THEN discount_amount
				WHEN 'tax' THEN tax
				WHEN 'payment_total' THEN payment_total
				WHEN 'payment_type' THEN payment_type
				WHEN 'is_paid' THEN is_paid
				WHEN 'order_status' THEN order_status
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'user_id' THEN user_id
				WHEN 'address' THEN address
				WHEN 'phone' THEN phone
				WHEN 'payment_amount' THEN payment_amount
				WHEN 'ship_cost' THEN ship_cost
				WHEN 'discount_amount' THEN discount_amount
				WHEN 'tax' THEN tax
				WHEN 'payment_total' THEN payment_total
				WHEN 'payment_type' THEN payment_type
				WHEN 'is_paid' THEN is_paid
				WHEN 'order_status' THEN order_status
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllLabels(IN in_sort_by varchar(25),
                                                               IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                               IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_label where is_del=false;
  end if;
  
  SELECT * FROM tbl_label 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'icon' THEN icon
				WHEN 'title' THEN title
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'icon' THEN icon
				WHEN 'title' THEN title
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllLogSystems(IN in_sort_by varchar(25),
                                                                   IN in_sort_dir enum ('ASC', 'DESC'),
                                                                   IN in_offset int, IN in_count int)
BEGIN
  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_log_system;
  end if;
  
  SELECT * FROM tbl_log_system 
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'action_table' THEN action_table
        WHEN 'action_time' THEN action_time
        WHEN 'action_by' THEN action_by
        WHEN 'action_name' THEN action_name
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'action_table' THEN action_table
        WHEN 'action_time' THEN action_time
        WHEN 'action_by' THEN action_by
        WHEN 'action_name' THEN action_name
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllProductImages(IN in_sort_by varchar(25),
                                                                      IN in_sort_dir enum ('ASC', 'DESC'),
                                                                      IN in_offset int, IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_product_image where is_del=false;
  end if;
  
  SELECT * FROM tbl_product_image 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'feedback_id' THEN feedback_id
				WHEN 'type' THEN type
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'feedback_id' THEN feedback_id
				WHEN 'type' THEN type
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllProductUnits(IN in_sort_by varchar(25),
                                                                     IN in_sort_dir enum ('ASC', 'DESC'),
                                                                     IN in_offset int, IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_product_unit where is_del=false;
  end if;
  
  SELECT * FROM tbl_product_unit 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'invoice_id' THEN invoice_id
				WHEN 'quantity' THEN quantity
				WHEN 'price' THEN price
				WHEN 'discount_price' THEN discount_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'invoice_id' THEN invoice_id
				WHEN 'quantity' THEN quantity
				WHEN 'price' THEN price
				WHEN 'discount_price' THEN discount_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllProducts(IN in_sort_by varchar(25),
                                                                 IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                                 IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_product where is_del=false;
  end if;
  
  SELECT * FROM tbl_product 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'brand_id' THEN brand_id
				WHEN 'category_id' THEN category_id
				WHEN 'name' THEN name
				WHEN 'released_date' THEN released_date
				WHEN 'quantity_in_stock' THEN quantity_in_stock
				WHEN 'listed_price' THEN listed_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'brand_id' THEN brand_id
				WHEN 'category_id' THEN category_id
				WHEN 'name' THEN name
				WHEN 'released_date' THEN released_date
				WHEN 'quantity_in_stock' THEN quantity_in_stock
				WHEN 'listed_price' THEN listed_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllRoles(IN in_sort_by varchar(25),
                                                              IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                              IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_role where is_del=false;
  end if;
  
  SELECT * FROM tbl_role 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindAllUsers(IN in_sort_by varchar(25),
                                                              IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,
                                                              IN in_count int)
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  if in_offset is null then
    set in_offset = 0;
  end if;
  if in_count is null then
    select count(*) into in_count from tbl_user where is_del=false;
  end if;
  
  SELECT * FROM tbl_user 
  WHERE is_del=false
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'gender' THEN gender
				WHEN 'date_of_birth' THEN date_of_birth
				WHEN 'email' THEN email
				WHEN 'is_active' THEN is_active
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'gender' THEN gender
				WHEN 'date_of_birth' THEN date_of_birth
				WHEN 'email' THEN email
				WHEN 'is_active' THEN is_active
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC
  LIMIT in_offset, in_count;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindBannerByDate(IN in_date date)
BEGIN
select * from tbl_banner where in_date between used_date and ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindBannerByDateRange(IN in_started_date date, IN in_ended_date date)
BEGIN
select * from tbl_banner where ended_date >= in_started_date or used_date <= in_ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindBannerByFilter(IN in_path varchar(255), IN in_type varchar(50),
                                                                    IN in_title varchar(100),
                                                                    IN in_link_product varchar(255),
                                                                    IN in_used_date date, IN in_ended_date date,
                                                                    IN in_created_date date, IN in_modified_date date,
                                                                    IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                    IN in_update_by varchar(100),
                                                                    IN in_sort_by varchar(25),
                                                                    IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_path = concat('%', in_path, '%');
set in_type = concat('%', in_type, '%');
set in_title = concat('%', in_title, '%');
set in_link_product = concat('%', in_link_product, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_banner 
  WHERE ((in_path=null) or (path like in_path))
and ((in_type=null) or (type like in_type))
and ((in_title=null) or (title like in_title))
and ((in_link_product=null) or (link_product like in_link_product))
and ((in_used_date=null) or (used_date=in_used_date))
and ((in_ended_date=null) or (ended_date=in_ended_date))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'path' THEN path
				WHEN 'type' THEN type
				WHEN 'title' THEN title
				WHEN 'link_product' THEN link_product
				WHEN 'used_date' THEN used_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'path' THEN path
				WHEN 'type' THEN type
				WHEN 'title' THEN title
				WHEN 'link_product' THEN link_product
				WHEN 'used_date' THEN used_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindBannerById(IN in_id bigint)
BEGIN
SELECT * FROM tbl_banner WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindBrandByFilter(IN in_name varchar(100), IN in_country varchar(100),
                                                                   IN in_establish_year year, IN in_logo varchar(255),
                                                                   IN in_created_date datetime,
                                                                   IN in_modified_date datetime,
                                                                   IN in_deleted_date datetime, IN in_is_del tinyint(1),
                                                                   IN in_update_by varchar(100),
                                                                   IN in_sort_by varchar(25),
                                                                   IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_name = concat('%', in_name, '%');
set in_country = concat('%', in_country, '%');
set in_logo = concat('%', in_logo, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_brand 
  WHERE ((in_name=null) or (name like in_name))
and ((in_country=null) or (country like in_country))
and ((in_establish_year=null) or (year(establish_date)=in_establish_year))
and ((in_logo=null) or (logo like in_logo))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'country' THEN country
				WHEN 'establish_date' THEN establish_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'country' THEN country
				WHEN 'establish_date' THEN establish_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindBrandById(IN in_id bigint)
BEGIN
SELECT * FROM tbl_brand WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCartByUserId(IN in_user_id bigint)
BEGIN
SELECT * FROM tbl_cart WHERE user_id=in_user_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCategoryByFilter(IN in_name varchar(50), IN in_image varchar(255),
                                                                      IN in_description varchar(255),
                                                                      IN in_created_date date, IN in_modified_date date,
                                                                      IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                      IN in_update_by varchar(100),
                                                                      IN in_sort_by varchar(25),
                                                                      IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_name = concat('%', in_name, '%');
set in_image = concat('%', in_image, '%');
set in_description = concat('%', in_description, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_category 
  WHERE ((in_name=null) or (name like in_name))
and ((in_image=null) or (image like in_image))
and ((in_description=null) or (description like in_description))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCategoryById(IN in_id bigint)
BEGIN
SELECT * FROM tbl_category WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCommentByFilter(IN in_root_comment_id varchar(25),
                                                                     IN in_product_id varchar(50),
                                                                     IN in_username varchar(100), IN in_phone char(15),
                                                                     IN in_content varchar(255),
                                                                     IN in_created_date date, IN in_modified_date date,
                                                                     IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                     IN in_update_by varchar(100),
                                                                     IN in_sort_by varchar(25),
                                                                     IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_username = concat('%', in_username, '%');
set in_content = concat('%', in_content, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_comment 
  WHERE ((in_root_comment_id=null) or (root_comment_id=in_root_comment_id))
and ((in_product_id=null) or (product_id=in_product_id))
and ((in_username=null) or (username like in_username))
and ((in_phone=null) or (phone=in_phone))
and ((in_content=null) or (content like in_content))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'root_comment_id' THEN root_comment_id
				WHEN 'product_id' THEN product_id
				WHEN 'username' THEN username
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'root_comment_id' THEN root_comment_id
				WHEN 'product_id' THEN product_id
				WHEN 'username' THEN username
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCommentById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_comment WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCommentByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_comment WHERE product_id=in_product_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindCommentByRootCommentId(IN in_root_comment_id varchar(25))
BEGIN
SELECT * FROM tbl_comment WHERE root_comment_id=in_root_comment_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindDiscountByDateRange(IN in_applied_date date, IN in_ended_date date)
BEGIN
select * from tbl_discount where ended_date >= in_applied_date or applied_date <= in_ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindDiscountByFilter(IN in_code varchar(20), IN in_rate float,
                                                                      IN in_max_amount decimal(21, 4),
                                                                      IN in_applied_type enum ('PRODUCT', 'PURCHASE'),
                                                                      IN in_applied_date datetime,
                                                                      IN in_ended_date datetime,
                                                                      IN in_created_date date, IN in_modified_date date,
                                                                      IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                      IN in_update_by varchar(100),
                                                                      IN in_sort_by varchar(25),
                                                                      IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_code = concat('%', in_code, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_discount 
  WHERE ((in_code=null) or (code like in_code))
and ((in_rate=null) or (rate=in_rate))
and ((in_max_amount=null) or (max_amount=in_max_amount))
and ((in_applied_type=null) or (applied_type=in_applied_type))
and ((in_applied_date=null) or (applied_date=in_applied_date))
and ((in_ended_date=null) or (ended_date=in_ended_date))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'code' THEN code
				WHEN 'rate' THEN rate
				WHEN 'max_amount' THEN max_amount
				WHEN 'applied_type' THEN applied_type
				WHEN 'applied_date' THEN applied_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'code' THEN code
				WHEN 'rate' THEN rate
				WHEN 'max_amount' THEN max_amount
				WHEN 'applied_type' THEN applied_type
				WHEN 'applied_date' THEN applied_date
				WHEN 'ended_date' THEN ended_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindDiscountById(IN in_id bigint)
BEGIN
SELECT * FROM tbl_discount WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindDiscountByProductId(IN in_product_id varchar(50))
BEGIN
select * 
from tbl_discount
where id in 
( select discount_id from tbl_product_discount where product_id = in_product_id);
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindDiscountOfProductUseInDate(IN in_product_id varchar(50), IN in_date date)
BEGIN
select * 
from tbl_discount
where id in 
( select discount_id from tbl_product_discount where product_id = in_product_id)
and in_date between applied_date and ended_date
order by max_amount desc;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindFeedbackByFilter(IN in_product_id varchar(50),
                                                                      IN in_user_id bigint, IN in_content varchar(255),
                                                                      IN in_rating_point int, IN in_created_date date,
                                                                      IN in_modified_date date, IN in_deleted_date date,
                                                                      IN in_is_del tinyint(1),
                                                                      IN in_update_by varchar(100),
                                                                      IN in_sort_by varchar(25),
                                                                      IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_content = concat('%', in_content, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_feedback 
  WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_user_id=null) or (user_id=in_user_id))
and ((in_content=null) or (content like in_content))
and ((in_rating_point=null) or (rating_point=in_rating_point))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'user_id' THEN user_id
				WHEN 'rating_point' THEN rating_point
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'user_id' THEN user_id
				WHEN 'rating_point' THEN rating_point
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindFeedbackById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_feedback WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindFeedbackByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_feedback WHERE product_id=in_product_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindFeedbackByUserId(IN in_user_id bigint)
BEGIN
SELECT * FROM tbl_feedback WHERE user_id=in_user_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindImportByFilter(IN in_product_id varchar(50), IN in_quantity bigint,
                                                                    IN in_imported_price decimal(21, 4),
                                                                    IN in_imported_date date, IN in_created_date date,
                                                                    IN in_modified_date date, IN in_deleted_date date,
                                                                    IN in_is_del tinyint(1),
                                                                    IN in_update_by varchar(100),
                                                                    IN in_sort_by varchar(25),
                                                                    IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_import 
  WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_quantity=null) or (quantity=in_quantity))
and ((in_imported_price=null) or (imported_price=in_imported_price))
and ((in_imported_date=null) or (cast(imported_date as date)=in_imported_date))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'quantity' THEN quantity
				WHEN 'imported_price' THEN imported_price
				WHEN 'imported_date' THEN imported_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'quantity' THEN quantity
				WHEN 'imported_price' THEN imported_price
				WHEN 'imported_date' THEN imported_date
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindImportById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_import WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindImportByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_import WHERE product_id=in_product_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindImportProductByDate(IN in_date date)
BEGIN
select * from tbl_import where cast(imported_date as date) = in_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindImportProductByDateRange(IN in_started_date date, IN in_ended_date date)
BEGIN
select * from tbl_import where imported_date between in_started_date and in_ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindInvoiceByDate(IN in_date date)
BEGIN
select * from tbl_invoice where cast(created_date as date) = in_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindInvoiceByDateRange(IN in_started_date date, IN in_ended_date date)
BEGIN
select * from tbl_invoice where created_date between in_started_date and in_ended_date;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindInvoiceByFilter(IN in_user_id bigint, IN in_address varchar(255),
                                                                     IN in_phone char(15),
                                                                     IN in_payment_amount decimal(21, 4),
                                                                     IN in_ship_cost decimal(21, 4),
                                                                     IN in_discount_amount decimal(21, 4),
                                                                     IN in_tax decimal(21, 4),
                                                                     IN in_payment_total decimal(21, 4),
                                                                     IN in_payment_type varchar(50),
                                                                     IN in_is_paid tinyint(1),
                                                                     IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),
                                                                     IN in_note varchar(255), IN in_created_date date,
                                                                     IN in_modified_date date, IN in_deleted_date date,
                                                                     IN in_is_del tinyint(1),
                                                                     IN in_update_by varchar(100),
                                                                     IN in_sort_by varchar(25),
                                                                     IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_address = concat('%', in_address, '%');
set in_payment_type = concat('%', in_payment_type, '%');
set in_note = concat('%', in_note, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_invoice 
  WHERE ((in_user_id=null) or (user_id=in_user_id))
and ((in_address=null) or (address like in_address))
and ((in_phone=null) or (phone=in_phone))
and ((in_payment_amount=null) or (payment_amount=in_payment_amount))
and ((in_ship_cost=null) or (ship_cost=in_ship_cost))
and ((in_discount_amount=null) or (discount_amount=in_discount_amount))
and ((in_tax=null) or (tax=in_tax))
and ((in_payment_total=null) or (payment_total=in_payment_total))
and ((in_payment_type=null) or (payment_type like in_payment_type))
and ((in_is_paid=null) or (is_paid=in_is_paid))
and ((in_order_status=null) or (order_status=in_order_status))
and ((in_note=null) or (note like in_note))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'user_id' THEN user_id
				WHEN 'address' THEN address
				WHEN 'phone' THEN phone
				WHEN 'payment_amount' THEN payment_amount
				WHEN 'ship_cost' THEN ship_cost
				WHEN 'discount_amount' THEN discount_amount
				WHEN 'tax' THEN tax
				WHEN 'payment_total' THEN payment_total
				WHEN 'payment_type' THEN payment_type
				WHEN 'is_paid' THEN is_paid
				WHEN 'order_status' THEN order_status
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'user_id' THEN user_id
				WHEN 'address' THEN address
				WHEN 'phone' THEN phone
				WHEN 'payment_amount' THEN payment_amount
				WHEN 'ship_cost' THEN ship_cost
				WHEN 'discount_amount' THEN discount_amount
				WHEN 'tax' THEN tax
				WHEN 'payment_total' THEN payment_total
				WHEN 'payment_type' THEN payment_type
				WHEN 'is_paid' THEN is_paid
				WHEN 'order_status' THEN order_status
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindInvoiceById(IN in_id varchar(50))
BEGIN
SELECT * FROM tbl_invoice WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindInvoiceByOrderStatus(IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'))
BEGIN
SELECT * FROM tbl_invoice WHERE order_status=in_order_status;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindInvoiceByUserId(IN in_user_id bigint)
BEGIN
SELECT * FROM tbl_invoice WHERE user_id=in_user_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindLabelByFilter(IN in_name varchar(25), IN in_icon varchar(255),
                                                                   IN in_title varchar(50),
                                                                   IN in_description varchar(255),
                                                                   IN in_created_date date, IN in_modified_date date,
                                                                   IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                   IN in_update_by varchar(100),
                                                                   IN in_sort_by varchar(25),
                                                                   IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_name = concat('%', in_name, '%');
set in_icon = concat('%', in_icon, '%');
set in_title = concat('%', in_title, '%');
set in_description = concat('%', in_description, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_label 
  WHERE ((in_name=null) or (name like in_name))
and ((in_icon=null) or (icon like in_icon))
and ((in_title=null) or (title like in_title))
and ((in_description=null) or (description like in_description))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'icon' THEN icon
				WHEN 'title' THEN title
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'icon' THEN icon
				WHEN 'title' THEN title
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindLabelById(IN in_id bigint)
BEGIN
SELECT * FROM tbl_label WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindLabelByProductId(IN in_product_id varchar(25))
BEGIN
select * from tbl_label where id in (
  select label_id from tbl_product_label where product_id = in_product_id
);
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindLogSystemByFilter(IN in_action_table varchar(50),
                                                                       IN in_action_date date,
                                                                       IN in_action_by varchar(100),
                                                                       IN in_action_name varchar(25),
                                                                       IN in_sort_by varchar(25),
                                                                       IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN
  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_action_table = concat('%', in_action_table, '%');
  set in_action_by = concat('%', in_action_by, '%');
  set in_action_name = concat('%', in_action_name, '%');

  SELECT * FROM tbl_log_system 
  WHERE ((in_action_table=null) or (action_table like in_action_table))
  and ((in_action_date=null) or (cast(action_time as date) = in_action_date))
  and ((in_action_by=null) or (action_by like in_action_by))
  and ((in_action_name=null) or (action_name like in_action_name))

  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'action_table' THEN action_table
        WHEN 'action_time' THEN action_time
        WHEN 'action_by' THEN action_by
        WHEN 'action_name' THEN action_name
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'action_table' THEN action_table
        WHEN 'action_date' THEN action_time
        WHEN 'action_by' THEN action_by
        WHEN 'action_name' THEN action_name
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductByBrandId(IN in_brand_id bigint)
BEGIN
SELECT * FROM tbl_product WHERE brand_id=in_brand_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductByCategoryId(IN in_category_id bigint)
BEGIN
SELECT * FROM tbl_product WHERE category_id=in_category_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductByFilter(IN in_brand_id bigint, IN in_category_id bigint,
                                                                     IN in_name varchar(100), IN in_released_date date,
                                                                     IN in_quantity_in_stock int,
                                                                     IN in_listed_price decimal(21, 4),
                                                                     IN in_created_date date, IN in_modified_date date,
                                                                     IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                     IN in_update_by varchar(100),
                                                                     IN in_sort_by varchar(25),
                                                                     IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_name = concat('%', in_name, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_product 
  WHERE ((in_brand_id=null) or (brand_id=in_brand_id))
and ((in_category_id=null) or (category_id=in_category_id))
and ((in_name=null) or (name like in_name))
and ((in_released_date=null) or (released_date=in_released_date))
and ((in_quantity_in_stock=null) or (quantity_in_stock=in_quantity_in_stock))
and ((in_listed_price=null) or (listed_price=in_listed_price))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'brand_id' THEN brand_id
				WHEN 'category_id' THEN category_id
				WHEN 'name' THEN name
				WHEN 'released_date' THEN released_date
				WHEN 'quantity_in_stock' THEN quantity_in_stock
				WHEN 'listed_price' THEN listed_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'brand_id' THEN brand_id
				WHEN 'category_id' THEN category_id
				WHEN 'name' THEN name
				WHEN 'released_date' THEN released_date
				WHEN 'quantity_in_stock' THEN quantity_in_stock
				WHEN 'listed_price' THEN listed_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductById(IN in_id varchar(50))
BEGIN
SELECT * FROM tbl_product WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductByLabelId(IN in_label_id bigint(11))
BEGIN
select * from tbl_product where id in (
  select product_id from tbl_product_label where label_id = in_label_id
);
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductByPriceRange(IN start_price double, IN end_price double)
BEGIN
select * from tbl_product where listed_price between start_price and end_price;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductByReleasedYear(IN in_year year)
BEGIN
select * from tbl_product where year(released_date) = in_year;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductImageByFilter(IN in_product_id varchar(50),
                                                                          IN in_feedback_id varchar(25),
                                                                          IN in_url varchar(255),
                                                                          IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),
                                                                          IN in_created_date date,
                                                                          IN in_modified_date date,
                                                                          IN in_deleted_date date,
                                                                          IN in_is_del tinyint(1),
                                                                          IN in_update_by varchar(100),
                                                                          IN in_sort_by varchar(25),
                                                                          IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_url = concat('%', in_url, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_product_image 
  WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_feedback_id=null) or (feedback_id=in_feedback_id))
and ((in_url=null) or (url like in_url))
and ((in_type=null) or (type=in_type))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'feedback_id' THEN feedback_id
				WHEN 'type' THEN type
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'feedback_id' THEN feedback_id
				WHEN 'type' THEN type
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductImageById(IN in_id varchar(50))
BEGIN
SELECT * FROM tbl_product_image WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductImageByImageType(IN in_image_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'))
BEGIN
SELECT * FROM tbl_product_image WHERE type=in_image_type;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductImageByProductId(IN in_product_id varchar(50))
BEGIN
SELECT * FROM tbl_product_image WHERE product_id=in_product_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductUnitByCartId(IN in_cart_id varchar(50))
BEGIN
SELECT * FROM tbl_product_unit WHERE cart_id=in_cart_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductUnitByFilter(IN in_product_id varchar(50),
                                                                         IN in_cart_id varchar(50),
                                                                         IN in_invoice_id varchar(50),
                                                                         IN in_quantity int, IN in_price decimal(21, 4),
                                                                         IN in_discount_price decimal(21, 4),
                                                                         IN in_created_date date,
                                                                         IN in_modified_date date,
                                                                         IN in_deleted_date date,
                                                                         IN in_is_del tinyint(1),
                                                                         IN in_update_by varchar(100),
                                                                         IN in_sort_by varchar(25),
                                                                         IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_product_unit 
  WHERE ((in_product_id=null) or (product_id=in_product_id))
and ((in_cart_id=null) or (cart_id=in_cart_id))
and ((in_invoice_id=null) or (invoice_id=in_invoice_id))
and ((in_quantity=null) or (quantity=in_quantity))
and ((in_price=null) or (price=in_price))
and ((in_discount_price=null) or (discount_price=in_discount_price))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'invoice_id' THEN invoice_id
				WHEN 'quantity' THEN quantity
				WHEN 'price' THEN price
				WHEN 'discount_price' THEN discount_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'product_id' THEN product_id
				WHEN 'invoice_id' THEN invoice_id
				WHEN 'quantity' THEN quantity
				WHEN 'price' THEN price
				WHEN 'discount_price' THEN discount_price
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductUnitById(IN in_id varchar(25))
BEGIN
SELECT * FROM tbl_product_unit WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindProductUnitByInvoiceId(IN in_invoice_id varchar(50))
BEGIN
SELECT * FROM tbl_product_unit WHERE invoice_id=in_invoice_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindRefreshTokenByCode(IN in_code varchar(50))
BEGIN
  SELECT * FROM tbl_refresh_token WHERE code = in_code;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindRoleByFilter(IN in_name varchar(25),
                                                                  IN in_description varchar(100),
                                                                  IN in_created_date date, IN in_modified_date date,
                                                                  IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                  IN in_update_by varchar(100),
                                                                  IN in_sort_by varchar(25),
                                                                  IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_name = concat('%', in_name, '%');
set in_description = concat('%', in_description, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_role 
  WHERE ((in_name=null) or (name like in_name))
and ((in_description=null) or (description like in_description))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindRoleById(IN in_id int)
BEGIN
SELECT * FROM tbl_role WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindRoleByName(IN in_name varchar(25))
BEGIN
SELECT * FROM tbl_role WHERE name like in_name;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindRoleByUserId(IN in_user_id bigint(11))
BEGIN
select * from tbl_role where id in (
  select role_id from tbl_user_role where user_id = in_user_id
);
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindUserByFilter(IN in_name varchar(100),
                                                                  IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),
                                                                  IN in_date_of_birth date, IN in_phone char(15),
                                                                  IN in_email varchar(50), IN in_is_active tinyint(1),
                                                                  IN in_created_date date, IN in_modified_date date,
                                                                  IN in_deleted_date date, IN in_is_del tinyint(1),
                                                                  IN in_update_by varchar(100),
                                                                  IN in_sort_by varchar(25),
                                                                  IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

  if in_sort_dir is null then
    set in_sort_dir = 'ASC';
  end if;

  set in_name = concat('%', in_name, '%');
set in_email = concat('%', in_email, '%');
set in_update_by = concat('%', in_update_by, '%');

  SELECT * FROM tbl_user 
  WHERE ((in_name=null) or (name like in_name))
and ((in_gender=null) or (gender=in_gender))
and ((in_date_of_birth=null) or (date_of_birth=in_date_of_birth))
and ((in_phone=null) or (phone=in_phone))
and ((in_email=null) or (email like in_email))
and ((in_is_active=null) or (is_active=in_is_active))
and ((in_created_date=null) or (cast(created_date as date)=in_created_date))
and ((in_modified_date=null) or (cast(modified_date as date)=in_modified_date))
and ((in_deleted_date=null) or (cast(deleted_date as date)=in_deleted_date))
and ((in_is_del=null) or (is_del=in_is_del))
and ((in_update_by=null) or (update_by like in_update_by))
  ORDER BY 
  CASE in_sort_dir
    WHEN 'ASC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'gender' THEN gender
				WHEN 'date_of_birth' THEN date_of_birth
				WHEN 'email' THEN email
				WHEN 'is_active' THEN is_active
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END ASC,
  CASE in_sort_dir
    WHEN 'DESC' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
				WHEN 'gender' THEN gender
				WHEN 'date_of_birth' THEN date_of_birth
				WHEN 'email' THEN email
				WHEN 'is_active' THEN is_active
				WHEN 'created_date' THEN created_date
				WHEN 'modified_date' THEN modified_date
				WHEN 'deleted_date' THEN deleted_date
      END
    END DESC;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindUserById(IN in_id bigint)
BEGIN
SELECT * FROM tbl_user WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindUserByPhone(IN in_phone char(15))
BEGIN
SELECT * FROM tbl_user WHERE phone=in_phone;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_FindUserByRoleName(IN in_role_name varchar(25))
BEGIN
  set in_role_name = concat('%', in_role_name, '%');

  select * from tbl_user where id in (
    select ur.user_id 
    from tbl_user_role ur, tbl_role r 
    where r.name like in_role_name
    and ur.role_id = r.id
  );
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewAddress(IN in_id varchar(25), IN in_user_id bigint,
                                                                  IN in_country varchar(25), IN in_line1 varchar(25),
                                                                  IN in_line2 varchar(25), IN in_line3 varchar(25),
                                                                  IN in_street varchar(100),
                                                                  IN in_is_default tinyint(1),
                                                                  IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_address (id, user_id, country, line1, line2, line3, street, is_default, update_by) VALUES (in_id, in_user_id, in_country, in_line1, in_line2, in_line3, in_street, in_is_default, in_update_by);SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewBanner(IN in_path varchar(255), IN in_type varchar(50),
                                                                 IN in_title varchar(100),
                                                                 IN in_link_product varchar(255), IN in_used_date date,
                                                                 IN in_ended_date date, IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_banner (path, type, title, link_product, used_date, ended_date, update_by) VALUES (in_path, in_type, in_title, in_link_product, in_used_date, in_ended_date, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewBrand(IN in_name varchar(100), IN in_country varchar(100),
                                                                IN in_establish_date date, IN in_logo varchar(255),
                                                                IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_brand (name, country, establish_date, logo, update_by) VALUES (in_name, in_country, in_establish_date, in_logo, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewCart(IN in_id varchar(50), IN in_user_id bigint,
                                                               IN in_discount_id bigint, IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_cart (id, user_id, discount_id, update_by) VALUES (in_id, in_user_id, in_discount_id, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewCategory(IN in_name varchar(50), IN in_image varchar(255),
                                                                   IN in_description varchar(255),
                                                                   IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_category (name, image, description, update_by) VALUES (in_name, in_image, in_description, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewComment(IN in_id varchar(25),
                                                                  IN in_root_comment_id varchar(25),
                                                                  IN in_product_id varchar(50),
                                                                  IN in_username varchar(100), IN in_phone char(15),
                                                                  IN in_content varchar(255),
                                                                  IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_comment (id, root_comment_id, product_id, username, phone, content, update_by) VALUES (in_id, in_root_comment_id, in_product_id, in_username, in_phone, in_content, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewDiscount(IN in_code varchar(20), IN in_rate float,
                                                                   IN in_max_amount decimal(21, 4),
                                                                   IN in_applied_type enum ('PRODUCT', 'PURCHASE'),
                                                                   IN in_applied_date datetime,
                                                                   IN in_ended_date datetime,
                                                                   IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_discount (code, rate, max_amount, applied_type, applied_date, ended_date, update_by) VALUES (in_code, in_rate, in_max_amount, in_applied_type, in_applied_date, in_ended_date, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewFeedback(IN in_id varchar(25), IN in_product_id varchar(50),
                                                                   IN in_user_id bigint, IN in_content varchar(255),
                                                                   IN in_rating_point int, IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_feedback (id, product_id, user_id, content, rating_point, update_by) VALUES (in_id, in_product_id, in_user_id, in_content, in_rating_point, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewImport(IN in_id varchar(25), IN in_product_id varchar(50),
                                                                 IN in_quantity bigint,
                                                                 IN in_imported_price decimal(21, 4),
                                                                 IN in_imported_date datetime,
                                                                 IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_import (id, product_id, quantity, imported_price, imported_date, update_by) VALUES (in_id, in_product_id, in_quantity, in_imported_price, in_imported_date, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewInvoice(IN in_id varchar(25), IN in_user_id bigint,
                                                                  IN in_address varchar(255), IN in_phone char(15),
                                                                  IN in_payment_amount decimal(21, 4),
                                                                  IN in_ship_cost decimal(21, 4),
                                                                  IN in_discount_amount decimal(21, 4),
                                                                  IN in_tax decimal(21, 4),
                                                                  IN in_payment_total decimal(21, 4),
                                                                  IN in_payment_type varchar(50),
                                                                  IN in_is_paid tinyint(1),
                                                                  IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),
                                                                  IN in_note varchar(255), IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_invoice (id, user_id, address, phone, payment_amount, ship_cost, discount_amount, tax, payment_total, payment_type, is_paid, order_status, note, update_by) VALUES (in_id, in_user_id, in_address, in_phone, in_payment_amount, in_ship_cost, in_discount_amount, in_tax, in_payment_total, in_payment_type, in_is_paid, in_order_status, in_note, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewLabel(IN in_name varchar(25), IN in_icon varchar(255),
                                                                IN in_title varchar(50), IN in_description varchar(255),
                                                                IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_label (name, icon, title, description, update_by) VALUES (in_name, in_icon, in_title, in_description, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewProduct(IN in_id varchar(50), IN in_brand_id bigint,
                                                                  IN in_category_id bigint, IN in_name varchar(100),
                                                                  IN in_released_date date, IN in_quantity_in_stock int,
                                                                  IN in_listed_price decimal(19, 4),
                                                                  IN in_specifications text,
                                                                  IN in_description_detail text,
                                                                  IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_product (id, brand_id, category_id, name, released_date, quantity_in_stock, listed_price, specifications, description_detail, update_by) VALUES (in_id, in_brand_id, in_category_id, in_name, in_released_date, in_quantity_in_stock, in_listed_price, in_specifications, in_description_detail, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewProductImage(IN in_id varchar(50),
                                                                       IN in_product_id varchar(50),
                                                                       IN in_feedback_id varchar(25),
                                                                       IN in_url varchar(255),
                                                                       IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),
                                                                       IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_product_image (id, product_id, feedback_id, url, type, update_by) VALUES (in_id, in_product_id, in_feedback_id, in_url, in_type, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewProductUnit(IN in_id varchar(25),
                                                                      IN in_product_id varchar(50),
                                                                      IN in_cart_id varchar(50),
                                                                      IN in_invoice_id varchar(50), IN in_quantity int,
                                                                      IN in_price decimal(19, 4),
                                                                      IN in_discount_price decimal(19, 4),
                                                                      IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_product_unit (id, product_id, cart_id, invoice_id, quantity, price, discount_price, update_by) VALUES (in_id, in_product_id, in_cart_id, in_invoice_id, in_quantity, in_price, in_discount_price, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewRole(IN in_name varchar(25), IN in_description varchar(100),
                                                               IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_role (name, description, update_by) VALUES (in_name, in_description, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertNewUser(IN in_name varchar(100),
                                                               IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),
                                                               IN in_date_of_birth date, IN in_phone char(15),
                                                               IN in_email varchar(50), IN in_password varchar(100),
                                                               IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_user (name, gender, date_of_birth, phone, email, password, update_by) VALUES (in_name, in_gender, in_date_of_birth, in_phone, in_email, in_password, in_update_by); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertProductAccessory(IN in_product_id varchar(255), IN in_accessory_id bigint)
BEGIN
INSERT INTO tbl_product_accessory (product_id, accessory_id) VALUES (in_product_id, in_accessory_id); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertProductDiscount(IN in_product_id varchar(255), IN in_discount_id bigint)
BEGIN
INSERT INTO tbl_product_discount (product_id, discount_id) VALUES (in_product_id, in_discount_id); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertProductLabel(IN in_product_id varchar(255), IN in_label_id bigint)
BEGIN
INSERT INTO tbl_product_label (product_id, label_id) VALUES (in_product_id, in_label_id); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertRefreshToken(IN in_code varchar(50), IN in_user_id bigint(11))
BEGIN
  INSERT INTO tbl_refresh_token(code, user_id) VALUES (in_code, in_user_id);
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_InsertUserRole(IN in_user_id bigint, IN in_role_id bigint)
BEGIN
INSERT INTO tbl_user_role (user_id, role_id) VALUES (in_user_id, in_role_id); SELECT LAST_INSERT_ID();
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateAddress(IN in_id varchar(25), IN in_user_id bigint,
                                                               IN in_country varchar(25), IN in_line1 varchar(25),
                                                               IN in_line2 varchar(25), IN in_line3 varchar(25),
                                                               IN in_street varchar(100), IN in_is_default tinyint(1),
                                                               IN in_update_by varchar(100))
BEGIN
UPDATE tbl_address SET user_id=in_user_id, country=in_country, line1=in_line1, line2=in_line2, line3=in_line3, street=in_street, is_default=in_is_default, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateBanner(IN in_id bigint, IN in_path varchar(255),
                                                              IN in_type varchar(50), IN in_title varchar(100),
                                                              IN in_link_product varchar(255), IN in_used_date date,
                                                              IN in_ended_date date, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_banner SET path=in_path, type=in_type, title=in_title, link_product=in_link_product, used_date=in_used_date, ended_date=in_ended_date, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateBrand(IN in_id bigint, IN in_name varchar(100),
                                                             IN in_country varchar(100), IN in_establish_date date,
                                                             IN in_logo varchar(255), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_brand SET name=in_name, country=in_country, establish_date=in_establish_date, logo=in_logo, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateCart(IN in_id varchar(50), IN in_user_id bigint,
                                                            IN in_discount_id bigint, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_cart SET user_id=in_user_id, discount_id=in_discount_id, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateCategory(IN in_id bigint, IN in_name varchar(50),
                                                                IN in_image varchar(255),
                                                                IN in_description varchar(255),
                                                                IN in_update_by varchar(100))
BEGIN
UPDATE tbl_category SET name=in_name, image=in_image, description=in_description, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateComment(IN in_id varchar(25), IN in_root_comment_id varchar(25),
                                                               IN in_product_id varchar(50),
                                                               IN in_username varchar(100), IN in_phone char(15),
                                                               IN in_content varchar(255), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_comment SET root_comment_id=in_root_comment_id, product_id=in_product_id, username=in_username, phone=in_phone, content=in_content, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateDiscount(IN in_id bigint, IN in_code varchar(20),
                                                                IN in_rate float, IN in_max_amount decimal(19, 4),
                                                                IN in_applied_type enum ('PRODUCT', 'PURCHASE'),
                                                                IN in_applied_date datetime, IN in_ended_date datetime,
                                                                IN in_update_by varchar(100))
BEGIN
UPDATE tbl_discount SET code=in_code, rate=in_rate, max_amount=in_max_amount, applied_type=in_applied_type, applied_date=in_applied_date, ended_date=in_ended_date, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateFeedback(IN in_id varchar(25), IN in_product_id varchar(50),
                                                                IN in_user_id bigint, IN in_content varchar(255),
                                                                IN in_rating_point int, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_feedback SET product_id=in_product_id, user_id=in_user_id, content=in_content, rating_point=in_rating_point, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateImport(IN in_id varchar(25), IN in_product_id varchar(50),
                                                              IN in_quantity bigint,
                                                              IN in_imported_price decimal(19, 4),
                                                              IN in_imported_date datetime,
                                                              IN in_update_by varchar(100))
BEGIN
UPDATE tbl_import SET product_id=in_product_id, quantity=in_quantity, imported_price=in_imported_price, imported_date=in_imported_date, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateInvoice(IN in_id varchar(50), IN in_user_id bigint,
                                                               IN in_address varchar(255), IN in_phone char(15),
                                                               IN in_payment_amount decimal(19, 4),
                                                               IN in_ship_cost decimal(19, 4),
                                                               IN in_discount_amount decimal(19, 4),
                                                               IN in_tax decimal(19, 4),
                                                               IN in_payment_total decimal(19, 4),
                                                               IN in_payment_type varchar(50), IN in_is_paid tinyint(1),
                                                               IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),
                                                               IN in_note varchar(255), IN in_update_by varchar(100))
BEGIN
UPDATE tbl_invoice SET user_id=in_user_id, address=in_address, phone=in_phone, payment_amount=in_payment_amount, ship_cost=in_ship_cost, discount_amount=in_discount_amount, tax=in_tax, payment_total=in_payment_total, payment_type=in_payment_type, is_paid=in_is_paid, order_status=in_order_status, note=in_note, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateInvoiceOrderStatus(IN in_id varchar(50),
                                                                          IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),
                                                                          IN in_update_by varchar(100))
BEGIN
UPDATE tbl_invoice SET order_status=in_order_status, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateInvoicePaymentTypeAndPaidStatus(IN in_id varchar(50),
                                                                                       IN in_payment_type varchar(50),
                                                                                       IN in_is_paid tinyint(1),
                                                                                       IN in_update_by varchar(100))
BEGIN
UPDATE tbl_invoice SET payment_type=in_payment_type, is_paid=in_is_paid, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateLabel(IN in_id bigint, IN in_name varchar(25),
                                                             IN in_icon varchar(255), IN in_title varchar(50),
                                                             IN in_description varchar(255),
                                                             IN in_update_by varchar(100))
BEGIN
UPDATE tbl_label SET name=in_name, icon=in_icon, title=in_title, description=in_description, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateProduct(IN in_id varchar(50), IN in_brand_id bigint,
                                                               IN in_category_id bigint, IN in_name varchar(100),
                                                               IN in_released_date date, IN in_quantity_in_stock int,
                                                               IN in_listed_price decimal(19, 4),
                                                               IN in_specifications text, IN in_description_detail text,
                                                               IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product SET brand_id=in_brand_id, category_id=in_category_id, name=in_name, released_date=in_released_date, quantity_in_stock=in_quantity_in_stock, listed_price=in_listed_price, specifications=in_specifications, description_detail=in_description_detail, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateProductImage(IN in_id varchar(50), IN in_product_id varchar(50),
                                                                    IN in_feedback_id varchar(25),
                                                                    IN in_url varchar(255),
                                                                    IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),
                                                                    IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product_image SET product_id=in_product_id, feedback_id=in_feedback_id, url=in_url, type=in_type, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateProductImageUrlAndType(IN in_id varchar(50),
                                                                              IN in_url varchar(255),
                                                                              IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),
                                                                              IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product_image SET url=in_url, type=in_type, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateProductPrice(IN in_id varchar(50),
                                                                    IN in_listed_price decimal(19, 4),
                                                                    IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product SET listed_price=in_listed_price, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateProductUnit(IN in_id varchar(25), IN in_product_id varchar(50),
                                                                   IN in_cart_id varchar(50),
                                                                   IN in_invoice_id varchar(50), IN in_quantity int,
                                                                   IN in_price decimal(19, 4),
                                                                   IN in_discount_price decimal(19, 4),
                                                                   IN in_update_by varchar(100))
BEGIN
UPDATE tbl_product_unit SET product_id=in_product_id, cart_id=in_cart_id, invoice_id=in_invoice_id, quantity=in_quantity, price=in_price, discount_price=in_discount_price, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateRole(IN in_id int, IN in_name varchar(25),
                                                            IN in_description varchar(100),
                                                            IN in_update_by varchar(100))
BEGIN
UPDATE tbl_role SET name=in_name, description=in_description, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateUser(IN in_id bigint, IN in_name varchar(100),
                                                            IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),
                                                            IN in_date_of_birth date, IN in_phone char(15),
                                                            IN in_email varchar(50), IN in_password varchar(100),
                                                            IN in_update_by varchar(100))
BEGIN
UPDATE tbl_user SET name=in_name, gender=in_gender, date_of_birth=in_date_of_birth, phone=in_phone, email=in_email, password=in_password, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateUserAccountStatus(IN in_id bigint, IN in_password varchar(100),
                                                                         IN in_update_by varchar(100))
BEGIN
UPDATE tbl_user SET password=in_password, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateUserInformation(IN in_id bigint, IN in_name varchar(100),
                                                                       IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),
                                                                       IN in_date_of_birth date,
                                                                       IN in_email varchar(50),
                                                                       IN in_update_by varchar(100))
BEGIN
UPDATE tbl_user SET name=in_name, gender=in_gender, date_of_birth=in_date_of_birth, email=in_email, update_by=in_update_by WHERE id=in_id;
END;

create
    definer = youtube2_fi@`%.%.%.%` procedure sp_UpdateUserPassword(IN in_id bigint, IN in_password varchar(100),
                                                                    IN in_update_by varchar(100))
BEGIN
UPDATE tbl_user SET password=in_password, update_by=in_update_by WHERE id=in_id;
END;


