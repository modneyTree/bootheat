-- 1) BOOTH
INSERT INTO booth (booth_id, name, location, created_at)
VALUES (1, '핫도그부스', 'A동 앞', CURRENT_TIMESTAMP);

-- 2) BOOTH_TABLE (테이블 1,2,3)
INSERT INTO booth_table (table_id, booth_id, table_number, active, created_at)
VALUES (1, 1, 1, TRUE, CURRENT_TIMESTAMP);
INSERT INTO booth_table (table_id, booth_id, table_number, active, created_at)
VALUES (2, 1, 2, TRUE, CURRENT_TIMESTAMP);
INSERT INTO booth_table (table_id, booth_id, table_number, active, created_at)
VALUES (3, 1, 3, TRUE, CURRENT_TIMESTAMP);

-- 3) MENU_ITEM (판매중 3개)
INSERT INTO menu_item (menu_item_id, booth_id, name, category, price, available, description, model_url, preview_image, created_at)
VALUES (1, 1, '핫도그',     'FOOD', 4000, TRUE, NULL, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO menu_item (menu_item_id, booth_id, name, category, price, available, description, model_url, preview_image, created_at)
VALUES (2, 1, '치즈핫도그', 'FOOD', 5000, TRUE, NULL, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO menu_item (menu_item_id, booth_id, name, category, price, available, description, model_url, preview_image, created_at)
VALUES (3, 1, '콜라',       'FOOD', 2000, TRUE, NULL, NULL, NULL, CURRENT_TIMESTAMP);

-- 4) MANAGER_USER (부스당 1명)
-- password_hash는 아무 문자열이어도 무관(인증 미사용 시). 예시로 임의 문자열 사용.
INSERT INTO manager_user (manager_id, booth_id, username, password_hash, role, account_bank, account_no, account_holder, created_at)
VALUES (1, 1, '부스 운영자', '$2a$10$dummyhashdummyhashdummyhashdum', 'MANAGER',
        '카카오뱅크', '1234-323432-123', '홍길동', CURRENT_TIMESTAMP);

-- 5) IDENTITY 증가값 조정(다음 insert부터 충돌 방지)
ALTER TABLE booth        ALTER COLUMN booth_id        RESTART WITH 2;
ALTER TABLE booth_table  ALTER COLUMN table_id        RESTART WITH 4;
ALTER TABLE menu_item    ALTER COLUMN menu_item_id    RESTART WITH 4;
ALTER TABLE manager_user ALTER COLUMN manager_id      RESTART WITH 2;
