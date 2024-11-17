create database ticket;
use ticket;
create table ticket(
id int primary key auto_increment,
nome varchar (50),
email varchar (100),
telefone varchar(11),
titulo varchar (30),
descricao varchar (200),
situacao boolean default false,
data_criacao datetime DEFAULT CURRENT_TIMESTAMP 
);