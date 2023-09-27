create table car
(
    id SERIAL PRIMARY KEY,
    brand text not null,
    model text not null,
    price integer not null
);

insert into car(brand, model, price) VALUES ('Mercedes-Benz','AMG',2500000);
insert into car(brand, model, price) VALUES ('Ford','Mustang',1500000);
insert into car(brand, model, price) VALUES ('Rang Rover','Sport',4000000);

create table person
(
    id SERIAL PRIMARY KEY ,
    name text not null,
    age integer not null,
    driver_license boolean,
    car_id integer references car(id)
);

insert into person(name, age, driver_license, car_id)
values ('Ivan Ivanov', 18, true, 1),
values ('Stepan Ivanov', 16, false,2),
values ('Sergey Ivanov', 25, true, 3),
values ('Oleg Ivanov', 24, true, 4);
