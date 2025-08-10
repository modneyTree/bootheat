insert into booth(booth_id, name, location, created_at)
values (1, '핫도그부스', 'A동 앞', CURRENT_TIMESTAMP());

insert into booth_table(table_id, booth_id, table_number, active, created_at)
values (1, 1, 1, true, CURRENT_TIMESTAMP()),
       (2, 1, 2, true, CURRENT_TIMESTAMP()),
       (3, 1, 3, true, CURRENT_TIMESTAMP());

insert into menu_item(menu_item_id, booth_id, name, price, available, created_at)
values (1, 1, '핫도그', 4000, true, CURRENT_TIMESTAMP()),
       (2, 1, '치즈핫도그', 5000, true, CURRENT_TIMESTAMP()),
       (3, 1, '콜라', 2000, true, CURRENT_TIMESTAMP());
