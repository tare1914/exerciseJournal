create table okt (
	id			integer not null,
    dato		integer,
    tid			integer,
    prestasjon	integer,
    form		integer,
    notat		varchar(150),
    constraint okt_pk primary key (id)
);

create table ovelse (
	ovelsesnavn		varchar(30) not null primary key
);

create table apparatovelse (
	ovelsesnavn		varchar(30) not null primary key,
    kilo			integer,
    sett			integer,
    /*apparatnavn		varchar(30),*/
    constraint apparatovelse_fk foreign key (ovelsesnavn) references ovelse(ovelsesnavn)
														on update cascade
                                                        on delete cascade
);

create table apparat (
	apparatnavn		varchar(40) not null,
    ovelsesnavn		varchar(30) not null,
    beskrivelse		varchar(100),
    constraint apparat_pk primary key (apparatnavn),
    constraint apparat_fk foreign key (ovelsesnavn) references apparatovelse(ovelsesnavn)
													on update cascade
                                                    on delete cascade
);

create table friovelse (
	ovelsesnavn		varchar(30) not null primary key,
    beskrivelse		varchar(150),
    constraint friovelse_fk foreign key (ovelsesnavn) references ovelse(ovelsesnavn)
													on update cascade
													on delete cascade
);

create table ovelseiokt (
	oktid			integer not null,
    ovelsesnavn		varchar(30) not null,
    prestasjon		integer,
    constraint ovelseiokt_pk primary key (oktid),
    constraint ovelseiokt_fk1 foreign key (ovelsesnavn) references ovelse(ovelsesnavn)
													on update cascade
													on delete cascade,
	constraint ovelseiokt_fk2 foreign key (oktid) references okt(id)
												on update cascade
												on delete cascade
);

create table ovelsesgruppe (
	gruppenavn		varchar(50) primary key
);

create table ovelseigruppe (
	ovelsesnavn		varchar(30) not null,
    gruppenavn		varchar(50) not null,
    constraint ovelseigruppe_pk primary key (ovelsesnavn, gruppenavn),
    constraint ovelseigruppe_fk1 foreign key (ovelsesnavn) references ovelse(ovelsesnavn)
														on update cascade
                                                        on delete cascade,
	constraint ovelseigruppe_fk2 foreign key (gruppenavn) references ovelsesgruppe(gruppenavn)
														on update cascade
                                                        on delete cascade
);