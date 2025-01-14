-- 카테고리
insert into category (category_code, category_name, created_by) values ('TOP', '상의', 'test');
insert into category (category_code, category_name, created_by) values ('OUTER', '아우터', 'test');
insert into category (category_code, category_name, created_by) values ('PANTS', '바지', 'test');
insert into category (category_code, category_name, created_by) values ('SNEAKERS', '스니커즈', 'test');
insert into category (category_code, category_name, created_by) values ('BAG', '가방', 'test');
insert into category (category_code, category_name, created_by) values ('HAT', '모자', 'test');
insert into category (category_code, category_name, created_by) values ('SOCKS', '양말', 'test');
insert into category (category_code, category_name, created_by) values ('ACCESSORY', '액세서리', 'test');

-- 브랜드
insert into brand (brand_code, brand_name, created_by) values ('A', 'A', 'test');
insert into brand (brand_code, brand_name, created_by) values ('B', 'B', 'test');
insert into brand (brand_code, brand_name, created_by) values ('C', 'C', 'test');
insert into brand (brand_code, brand_name, created_by) values ('D', 'D', 'test');
insert into brand (brand_code, brand_name, created_by) values ('E', 'E', 'test');
insert into brand (brand_code, brand_name, created_by) values ('F', 'F', 'test');
insert into brand (brand_code, brand_name, created_by) values ('G', 'G', 'test');
insert into brand (brand_code, brand_name, created_by) values ('H', 'H', 'test');
insert into brand (brand_code, brand_name, created_by) values ('I', 'I', 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_TOP_1', 'A상의1', 'A', 'TOP', 11200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_OUTER_1', 'A아우터1', 'A', 'OUTER', 5500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_PANTS_1', 'A바지1', 'A', 'PANTS', 4200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_SNEAKERS_1', 'A스니커즈1', 'A', 'SNEAKERS', 9000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_BAG_1', 'A가방1', 'A', 'BAG', 2000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_HAT_1', 'A모자1', 'A', 'HAT', 1700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_SOCKS_1', 'A양말1', 'A', 'SOCKS', 1800, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('A_ACCESSORY_1', 'A액세서리1', 'A', 'ACCESSORY', 2300, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_TOP_1', 'B상의1', 'B', 'TOP', 10500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_OUTER_1', 'B아우터1', 'B', 'OUTER', 5900, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_PANTS_1', 'B바지1', 'B', 'PANTS', 3800, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_SNEAKERS_1', 'B스니커즈1', 'B', 'SNEAKERS', 9100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_BAG_1', 'B가방1', 'B', 'BAG', 2100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_HAT_1', 'B모자1', 'B', 'HAT', 2000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_SOCKS_1', 'B양말1', 'B', 'SOCKS', 2000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('B_ACCESSORY_1', 'B액세서리1', 'B', 'ACCESSORY', 2200, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_TOP_1', 'C상의1', 'C', 'TOP', 10000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_OUTER_1', 'C아우터1', 'C', 'OUTER', 6200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_PANTS_1', 'C바지1', 'C', 'PANTS', 3300, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_SNEAKERS_1', 'C스니커즈1', 'C', 'SNEAKERS', 9200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_BAG_1', 'C가방1', 'C', 'BAG', 2200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_HAT_1', 'C모자1', 'C', 'HAT', 1900, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_SOCKS_1', 'C양말1', 'C', 'SOCKS', 2200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('C_ACCESSORY_1', 'C액세서리1', 'C', 'ACCESSORY', 2100, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_TOP_1', 'D상의1', 'D', 'TOP', 10100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_OUTER_1', 'D아우터1', 'D', 'OUTER', 5100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_PANTS_1', 'D바지1', 'D', 'PANTS', 3000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_SNEAKERS_1', 'D스니커즈1', 'D', 'SNEAKERS', 9500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_BAG_1', 'D가방1', 'D', 'BAG', 2500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_HAT_1', 'D모자1', 'D', 'HAT', 1500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_SOCKS_1', 'D양말1', 'D', 'SOCKS', 2400, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('D_ACCESSORY_1', 'D액세서리1', 'D', 'ACCESSORY', 2000, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_TOP_1', 'E상의1', 'E', 'TOP', 10700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_OUTER_1', 'E아우터1', 'E', 'OUTER', 5000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_PANTS_1', 'E바지1', 'E', 'PANTS', 3800, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_SNEAKERS_1', 'E스니커즈1', 'E', 'SNEAKERS', 9900, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_BAG_1', 'E가방1', 'E', 'BAG', 2300, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_HAT_1', 'E모자1', 'E', 'HAT', 1800, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_SOCKS_1', 'E양말1', 'E', 'SOCKS', 2100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('E_ACCESSORY_1', 'E액세서리1', 'E', 'ACCESSORY', 2100, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_TOP_1', 'F상의1', 'F', 'TOP', 11200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_OUTER_1', 'F아우터1', 'F', 'OUTER', 7200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_PANTS_1', 'F바지1', 'F', 'PANTS', 4000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_SNEAKERS_1', 'F스니커즈1', 'F', 'SNEAKERS', 9300, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_BAG_1', 'F가방1', 'F', 'BAG', 2100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_HAT_1', 'F모자1', 'F', 'HAT', 1600, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_SOCKS_1', 'F양말1', 'F', 'SOCKS', 2300, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('F_ACCESSORY_1', 'F액세서리1', 'F', 'ACCESSORY', 1900, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_TOP_1', 'G상의1', 'G', 'TOP', 10500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_OUTER_1', 'G아우터1', 'G', 'OUTER', 5800, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_PANTS_1', 'G바지1', 'G', 'PANTS', 3900, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_SNEAKERS_1', 'G스니커즈1', 'G', 'SNEAKERS', 9000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_BAG_1', 'G가방1', 'G', 'BAG', 2200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_HAT_1', 'G모자1', 'G', 'HAT', 1700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_SOCKS_1', 'G양말1', 'G', 'SOCKS', 2100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('G_ACCESSORY_1', 'G액세서리1', 'G', 'ACCESSORY', 2000, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_TOP_1', 'H상의1', 'H', 'TOP', 10800, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_OUTER_1', 'H아우터1', 'H', 'OUTER', 6300, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_PANTS_1', 'H바지1', 'H', 'PANTS', 3100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_SNEAKERS_1', 'H스니커즈1', 'H', 'SNEAKERS', 9700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_BAG_1', 'H가방1', 'H', 'BAG', 2100, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_HAT_1', 'H모자1', 'H', 'HAT', 1600, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_SOCKS_1', 'H양말1', 'H', 'SOCKS', 2000, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('H_ACCESSORY_1', 'H액세서리1', 'H', 'ACCESSORY', 2000, 'test');

insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_TOP_1', 'I상의1', 'I', 'TOP', 11400, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_OUTER_1', 'I아우터1', 'I', 'OUTER', 6700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_PANTS_1', 'I바지1', 'I', 'PANTS', 3200, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_SNEAKERS_1', 'I스니커즈1', 'I', 'SNEAKERS', 9500, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_BAG_1', 'I가방1', 'I', 'BAG', 2400, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_HAT_1', 'I모자1', 'I', 'HAT', 1700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_SOCKS_1', 'I양말1', 'I', 'SOCKS', 1700, 'test');
insert into product (product_no, product_name, brand_code, category_code, price, created_by) values ('I_ACCESSORY_1', 'I액세서리1', 'I', 'ACCESSORY', 2400, 'test');