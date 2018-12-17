
    create table ingredient (
       id bigint not null auto_increment,
        name varchar(255),
        type varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table taco (
       id bigint not null auto_increment,
        created_at datetime,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table taco_order (
       id bigint not null auto_increment,
        cccvv varchar(255),
        cc_expiration varchar(255),
        cc_number varchar(255),
        delivery_city varchar(255),
        delivery_name varchar(255),
        delivery_state varchar(255),
        delivery_street varchar(255),
        delivery_zip varchar(255),
        placed_at datetime,
        primary key (id)
    ) engine=InnoDB;

    create table taco_ingredients (
       taco_id bigint not null,
        ingredients_id bigint not null,
        primary key (taco_id, ingredients_id)
    ) engine=InnoDB;

    create table taco_order_tacos (
       order_id bigint not null,
        tacos_id bigint not null,
        primary key (order_id, tacos_id)
    ) engine=InnoDB;

    alter table taco_ingredients 
       add constraint FK7y679y77n5e75s3ss1v7ff14j 
       foreign key (ingredients_id) 
       references ingredient (id);

    alter table taco_ingredients 
       add constraint FK27rycuh3mjaepnba0j6m8xl4q 
       foreign key (taco_id) 
       references taco (id);

    alter table taco_order_tacos 
       add constraint FKfwvqtnjfview9e5f7bfqtd1ns 
       foreign key (tacos_id) 
       references taco (id);

    alter table taco_order_tacos 
       add constraint FKcxwvdkndaqmrxcen10vkneexo 
       foreign key (order_id) 
       references taco_order (id);
