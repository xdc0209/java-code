drop table Rant if exists
drop table Vehicle if exists
drop table Motorist_Privileges if exists
drop table Motorist if exists

create table Rant (
  id integer identity primary key,
  vehicle_id integer not null,
  rantText varchar(2000) not null,
  postedDate date not null
);

create table Motorist (
  id integer identity primary key,
  email varchar(255) not null,
  password varchar(50) not null,
  firstName varchar(30) not null,
  lastName varchar(30) not null
);

create table Vehicle (
  id integer identity primary key,
  motorist_id integer,
  state varchar(2) not null,
  plateNumber varchar(10) not null
);

create table Motorist_Privileges (
  motorist_id integer not null,
  privilege varchar(30) not null
);

alter table Vehicle add constraint vehicle_motorist foreign key (motorist_id) references Motorist;
alter table Rant add constraint rant_vehicle foreign key (vehicle_id) references Vehicle;
alter table Motorist_Privileges add constraint priv_motorist foreign key (motorist_id) references Motorist;
