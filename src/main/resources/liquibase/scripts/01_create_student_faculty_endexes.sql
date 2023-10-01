--liquibase formatted sql

--changeset kalyashov-ga:create_student_name_index
--comment: Индекс для поиска по имени студента
create index student_name_index on students(name);

--changeset kalyashov-ga:create_faculty_name_color_index
--comment: Индекс для поиска по названию и цвету факультета
create index faculty_name_color_index on faculties(name,color);