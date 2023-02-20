use youtube2_laptech;
DELIMITER $$
CREATE PROCEDURE sp_GetListUser(IN in_sort_by varchar(25), IN in_sort_dir char(4), IN in_offset int, IN in_count int)
BEGIN
	if in_sort_dir is null or in_sort_dir != 'desc' then
		set in_sort_dir = 'asc';
	end if;

	if in_offset is null then
		set in_offset = 0;
  end if;
    
  if in_count is null then
		select count(*) into in_count from tbl_user;
  end if;

	SELECT * FROM tbl_user
  ORDER BY 
    CASE LOWER(in_sort_dir) WHEN 'asc' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
        WHEN 'gender' THEN gender
        WHEN 'date_of_birth' THEN date_of_birth
        WHEN 'phone' THEN phone
        WHEN 'email' THEN email
        WHEN 'is_active' THEN is_active
        WHEN 'created_date' THEN created_date
        WHEN 'modified_date' THEN modified_date
      END
    END ASC, 
    CASE LOWER(in_sort_dir) WHEN 'desc' THEN
      CASE LOWER(in_sort_by)
        WHEN 'name' THEN name
        WHEN 'gender' THEN gender
        WHEN 'date_of_birth' THEN date_of_birth
        WHEN 'phone' THEN phone
        WHEN 'email' THEN email
        WHEN 'is_active' THEN is_active
        WHEN 'created_date' THEN created_date
        WHEN 'modified_date' THEN modified_date
      END
    END DESC
  LIMIT IN_OFFSET , IN_COUNT;
END $$