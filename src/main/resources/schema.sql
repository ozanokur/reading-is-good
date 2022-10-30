drop sequence if exists sequence;

drop table if exists book_order, customer, book;
	
CREATE SEQUENCE "SEQUENCE" 
MINVALUE 1 
MAXVALUE 999999999 
INCREMENT BY 1 
START WITH 202700 
NOCACHE 
NOCYCLE;
	


create table book (
   id int8 not null,
    count int8 not null,
    name varchar(255) not null,
    price numeric(19, 2) not null,
    primary key (id)
);

create table customer (
   id int8 not null,
    email varchar(255) not null unique,
    name varchar(255) not null,
    primary key (id)
);

create table book_order (
   id int8 not null,
    book_id int8 not null,
    count int8 not null,
    customer_id int8 not null,
    date timestamp not null,
    primary key (id),
	constraint fk_customer
	  foreign key(customer_id) 
	  references customer(id),
	constraint fk_book
	  foreign key(book_id) 
	  references book(id)
);

create table app_user (
   id int8 not null,
    password varchar(255) not null,
    roles varchar(255) not null,
    username varchar(255) not null unique,
    primary key (id)
)