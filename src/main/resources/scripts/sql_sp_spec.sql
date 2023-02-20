use youtube2_laptech;
DELIMITER $$

CREATE PROCEDURE sp_FindBannerByDateRange(IN in_started_date date, IN in_ended_date date)
BEGIN
select * from tbl_banner where ended_date >= in_started_date or used_date <= in_ended_date;
END $$

CREATE PROCEDURE sp_FindBannerByDate(IN in_date date)
BEGIN
select * from tbl_banner where in_date between used_date and ended_date;
END $$

CREATE PROCEDURE sp_FindDiscountOfProductUseInDate(IN in_product_id varchar(50), IN in_date date)
BEGIN
select *
from tbl_discount
where in_date between applied_date and ended_date
and id in (
    select discount_id from tbl_product_discount where product_id = in_product_id
)
limit 1;
END $$

CREATE PROCEDURE sp_FindDiscountByCode(IN in_code varchar(20))
BEGIN
select *
from tbl_discount
where code LIKE '%' + in_code + '%';
END $$

CREATE PROCEDURE sp_FindDiscountByProductId(IN in_product_id varchar(50))
BEGIN
select *
from tbl_discount
where id in (
    select discount_id from tbl_product_discount where product_id = in_product_id
);
END $$

CREATE PROCEDURE sp_FindDiscountByDateRange(IN in_applied_date date, IN in_ended_date date)
BEGIN
select * from tbl_discount where ended_date >= in_applied_date or applied_date <= in_ended_date;
END $$

CREATE PROCEDURE sp_FindImportProductByDate(IN in_date date)
BEGIN
select * from tbl_import where cast(import_date as date) = in_date;
END $$

CREATE PROCEDURE sp_FindImportProductByDateRange(IN in_started_date date, IN in_ended_date date)
BEGIN
select * from tbl_import where import_date between in_started_date and in_ended_date;
END $$

CREATE PROCEDURE sp_FindInvoiceByAddress(IN in_address varchar(255))
BEGIN
select * from tbl_invoice where address like '%' + in_address + '%';
END $$

CREATE PROCEDURE sp_FindInvoiceByDate(IN in_date date)
BEGIN
select * from tbl_invoice where cast(created_date as date) = in_date;
END $$

CREATE PROCEDURE sp_FindInvoiceByDateRange(IN in_started_date date, IN in_ended_date date)
BEGIN
select * from tbl_invoice where created_date between in_started_date and in_ended_date;
END $$

CREATE PROCEDURE sp_FindLabelByProductId(IN in_product_id varchar(25))
BEGIN
select * from tbl_label where id in (
  select label_id from tbl_product_label where product_id = in_product_id
);
END $$

CREATE PROCEDURE sp_FindLabelByName(IN in_name varchar(25))
BEGIN
select * from tbl_label where name like '%' + in_name + '%';
END $$

CREATE PROCEDURE sp_FindLabelByTitle(IN in_title varchar(50))
BEGIN
select * from tbl_label where name like '%' + in_title + '%';
END $$

CREATE PROCEDURE sp_FindAccessoryByProductId(IN in_product_id varchar(50))
BEGIN
select * from tbl_product where id in (
  select accessory_id from tbl_product_accessory where product_id = in_product_id
);
END $$

CREATE PROCEDURE sp_FindProductByName(IN in_name varchar(100))
BEGIN
select * from tbl_product where name like '%' + in_name + '%';
END $$

CREATE PROCEDURE sp_FindProductByReleasedYear(IN in_year year)
BEGIN
select * from tbl_product where year(released_date) = in_year;
END $$

CREATE PROCEDURE sp_FindProductByPriceRange(IN start_price double, IN end_price double)
BEGIN
select * from tbl_product where listed_price between start_price and end_price;
END $$

CREATE PROCEDURE sp_FindProductByLabelId(IN in_label_id bigint(11))
BEGIN
select * from tbl_product where id in (
  select product_id from tbl_product_label where label_id = in_label_id
);
END $$

CREATE PROCEDURE sp_FindRoleByUserId(IN in_user_id bigint(11))
BEGIN
select * from tbl_role where id in (
  select role_id from tbl_user_role where user_id = in_user_id
);
END $$

CREATE PROCEDURE sp_FindUserByRoleName(IN in_role_name varchar(25))
BEGIN
  select * from tbl_user where id in (
    select ur.user_id 
    from tbl_user_role ur, tbl_role r 
    where r.name like '%' + in_role_name + '%'
    and ur.role_id = r.id
  );
END $$

CREATE PROCEDURE sp_FindUserByName(IN in_name varchar(100))
BEGIN
  select * from tbl_user where name like '%' + in_name + '%';
END $$

CREATE PROCEDURE sp_DeleteUser(IN in_user_id varchar(50))
BEGIN
  delete from tbl_user where id=in_user_id and is_active=0;
END $$