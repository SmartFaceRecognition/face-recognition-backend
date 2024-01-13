insert into wharf value (1,'제 1 부두') ON DUPLICATE KEY UPDATE wharf_id=wharf_id;
insert into wharf value (2,'제 2 부두') ON DUPLICATE KEY UPDATE wharf_id=wharf_id;
insert into wharf value (3,'제 3 부두') ON DUPLICATE KEY UPDATE wharf_id=wharf_id;