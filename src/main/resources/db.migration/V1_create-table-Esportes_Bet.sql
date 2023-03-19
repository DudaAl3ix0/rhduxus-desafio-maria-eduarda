create table integrante(
    id bigint not null auto_increment,
    nome varchar(100) not null,
    franquia varchar(60) not null,
    funcao varchar(60) not null,

    primary key (id)
);

create table time (
    id bigint not null auto_increment,
    data int not null,

    primary key (id)
);

create table composicao_time(
    id bigint not null auto_increment,
    integrante_id bigint not null,
    time_id bigint not null,
    FOREIGN KEY (time_id) REFERENCES time(id),
    FOREIGN KEY (integrante_id) REFERENCES integrante(id),

    primary key (id)

);

