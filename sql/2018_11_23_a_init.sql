create database proxifood;
create user 'proxifood'@'localhost' identified by 'ThePassword';
grant all on proxifood.* to 'proxifood'@'localhost';