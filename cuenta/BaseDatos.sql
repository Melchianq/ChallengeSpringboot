
    create table cuentas (
        saldo_inicial float(53) not null,
        id_cliente bigint not null,
        id_cuenta bigint not null auto_increment,
        estado varchar(255) not null,
        numero_cuenta varchar(255) not null,
        tipo_cuenta varchar(255) not null,
        primary key (id_cuenta)
    ) engine=InnoDB;

    create table movimientos (
        fecha date not null,
        saldo float(53) not null,
        valor float(53) not null,
        id_cuenta bigint not null,
        id_movimiento bigint not null auto_increment,
        tipo_movimiento varchar(255) not null,
        primary key (id_movimiento)
    ) engine=InnoDB;

    alter table movimientos 
       add constraint FKlebenvy2r7bf11m3tfi8uc9gu 
       foreign key (id_cuenta) 
       references cuentas (id_cuenta);
