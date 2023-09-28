- Возраст студента не может быть меньше 16 лет.

ALTER TABLE studets
     ADD CONSTRAIN age_check check(age >= 16);

- Имена студентов должны быть уникальными и не равны нулю.

ALTER TABLE studets
    ADD CONSTRAIN name_unique UNIQUE(name);

ALTER TABLE studets
    alter column name set not null;

- Пара “значение названия” - “цвет факультета” должна быть уникальной.

ALTER TABLE faculties
    ADD CONSTRAIN name_color_unique UNIQUE(name,color);

- При создании студента без возраста ему автоматически должно присваиваться 20 лет.

ALTER TABLE studets
    alter column name set default 20;