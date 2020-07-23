CREATE DATABASE news_portal;
\c news_portal;
CREATE TABLE users (id serial primary key, name varchar, position varchar, role varchar, department varchar);
CREATE TABLE news (id serial primary key, title varchar, description varchar, type varchar, author varchar);
CREATE TABLE departments (id serial primary key, name varchar, description varchar, totalemployees int);
CREATE TABLE departments_users (id serial primary key, deptid int, userid int);
CREATE TABLE departments_news (id serial primary key, deptid int, newsid int, userid int);
CREATE DATABASE news_portal_test WITH TEMPLATE news_portal;