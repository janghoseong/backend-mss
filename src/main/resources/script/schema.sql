drop table if exists category cascade;
drop table if exists brand cascade;
drop table if exists product cascade;

-- 카테고리 테이블
create table category (
    id bigint not null auto_increment primary key,
    category_code varchar(20) not null unique,
    category_name varchar(100),
    created_at timestamp default now(),
    created_by varchar(20),
    updated_at timestamp,
    updated_by varchar(20)
);

-- 브랜드 테이블
create table brand (
  id bigint not null auto_increment primary key,
  brand_code varchar(20) not null unique,
  brand_name varchar(100),
  created_at timestamp default now(),
  created_by varchar(20),
  updated_at timestamp,
  updated_by varchar(20)
);

-- 상품 테이블
create table product (
    id bigint not null auto_increment primary key,
    product_no varchar(20) not null,
    product_name varchar(300),
    brand_code varchar(20) not null,
    category_code varchar(20) not null,
    price bigint not null,
    created_at timestamp default now(),
    created_by varchar(20),
    updated_at timestamp,
    updated_by varchar(20)
);

create index product_index01 ON product (brand_code);
create index product_index02 ON product (category_code);
create index product_index03 ON product (brand_code, category_code);
create index product_index04 ON product (category_code, price);