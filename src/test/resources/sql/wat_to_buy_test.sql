CREATE TABLE category_product
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name            VARCHAR(255),
    family_group_id INTEGER                                  NOT NULL,
    CONSTRAINT category_product_pkey PRIMARY KEY (id)
);

CREATE TABLE famaly_group_invitations
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    family_group_id INTEGER                                  NOT NULL,
    user_id         INTEGER                                  NOT NULL,
    CONSTRAINT famaly_group_invitations_pkey PRIMARY KEY (id)
);

CREATE TABLE family_group
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 2) NOT NULL,
    name            VARCHAR(255),
    user_creator_id INTEGER                                                 NOT NULL,
    CONSTRAINT family_group_pkey PRIMARY KEY (id)
);

CREATE TABLE point_shopping
(
    id                 INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    address            VARCHAR(255),
    comment            VARCHAR(255),
    create_date        TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    last_modified      TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255),
    family_group_id    INTEGER                                  NOT NULL,
    last_modified_user INTEGER,
    user_creator       INTEGER                                  NOT NULL,
    CONSTRAINT point_shopping_pkey PRIMARY KEY (id)
);

CREATE TABLE product
(
    id                  INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    create_date         TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    name                VARCHAR(255),
    unit_of_measurement SMALLINT,
    category_id         INTEGER,
    family_group_id     INTEGER                                  NOT NULL,
    subcategory_id      INTEGER,
    user_creator_id     INTEGER                                  NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE shopping
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    create_date      TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    executor_date    TIMESTAMP WITHOUT TIME ZONE,
    shopping_status  VARCHAR(255),
    volume           INTEGER,
    family_group_id  INTEGER                                  NOT NULL,
    point_id         INTEGER,
    product_id       INTEGER,
    shopping_project INTEGER,
    user_creator_id  INTEGER                                  NOT NULL,
    user_executor_id INTEGER,
    CONSTRAINT shopping_pkey PRIMARY KEY (id)
);

CREATE TABLE shopping_project
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    active          BOOLEAN                                  NOT NULL,
    comment         VARCHAR(255),
    create_date     TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    finish_date     TIMESTAMP WITHOUT TIME ZONE,
    last_modified   TIMESTAMP WITHOUT TIME ZONE,
    name            VARCHAR(255),
    family_group    INTEGER                                  NOT NULL,
    user_creator_id INTEGER                                  NOT NULL,
    CONSTRAINT shopping_project_pkey PRIMARY KEY (id)
);

CREATE TABLE subcategory_product
(
    id                  INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name                VARCHAR(255),
    category_product_id INTEGER                                  NOT NULL,
    family_group_id     INTEGER                                  NOT NULL,
    CONSTRAINT subcategory_product_pkey PRIMARY KEY (id)
);

CREATE TABLE token
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 7) NOT NULL,
    expired    BOOLEAN,
    revoked    BOOLEAN,
    token      VARCHAR(255),
    token_type VARCHAR(255),
    user_id    INTEGER,
    CONSTRAINT token_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 3) NOT NULL,
    create_date_time TIMESTAMP WITHOUT TIME ZONE,
    email            VARCHAR(255),
    firstname        VARCHAR(255),
    lastname         VARCHAR(255),
    password         VARCHAR(255),
    role             VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE famaly_group_invitations
    ADD CONSTRAINT uk_jwfe0n5gtvdxw7pkqq4nn4d45 UNIQUE (user_id);

ALTER TABLE famaly_group_invitations
    ADD CONSTRAINT uk_s6dykirwnk8ctoo9ykbhrp2cn UNIQUE (family_group_id);

CREATE TABLE family_users
(
    family_id INTEGER NOT NULL,
    user_id   INTEGER NOT NULL
);

ALTER TABLE shopping
    ADD CONSTRAINT fk17gdkmax5nxf5y74qq5hmvul3 FOREIGN KEY (point_id) REFERENCES point_shopping (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping
    ADD CONSTRAINT fk2d1fwynt5k6si35yfo8qdxxh6 FOREIGN KEY (product_id) REFERENCES product (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE famaly_group_invitations
    ADD CONSTRAINT fk3qgmkay9elqfpq3qfkiwgtq0l FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping
    ADD CONSTRAINT fk75er1igsxbla3441kpr0x8lsw FOREIGN KEY (family_group_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE point_shopping
    ADD CONSTRAINT fkarn0vk3ayl9qryyyutxjjmg57 FOREIGN KEY (family_group_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE family_users
    ADD CONSTRAINT fkatqpjy6cdk0sbnuflpvqx4hdw FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping
    ADD CONSTRAINT fkcj8uvsftx17vtxx49y70ppyf3 FOREIGN KEY (shopping_project) REFERENCES shopping_project (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE category_product
    ADD CONSTRAINT fkdew5a5dra4d5d8p1ryp3w48a3 FOREIGN KEY (family_group_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE subcategory_product
    ADD CONSTRAINT fke719n524xtylp86vnm6jjp3ka FOREIGN KEY (category_product_id) REFERENCES category_product (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping_project
    ADD CONSTRAINT fkg85futjsq6rtcfck0nf30790k FOREIGN KEY (family_group) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE family_users
    ADD CONSTRAINT fkgaqf00e79fs9s62tiificvpb7 FOREIGN KEY (family_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE product
    ADD CONSTRAINT fkgk4a2929ng6h26krlpyu1jqlc FOREIGN KEY (family_group_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE token
    ADD CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE product
    ADD CONSTRAINT fkkgq9rs9e7n28y64yp6wdyeo16 FOREIGN KEY (user_creator_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE point_shopping
    ADD CONSTRAINT fkkuprvxwuxjbic2lfidj7istp9 FOREIGN KEY (last_modified_user) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE product
    ADD CONSTRAINT fkl7j0yq3dq62ame6bf53rpjstw FOREIGN KEY (subcategory_id) REFERENCES subcategory_product (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE product
    ADD CONSTRAINT fkost67owd3gqs8mmqg971nx189 FOREIGN KEY (category_id) REFERENCES category_product (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE family_group
    ADD CONSTRAINT fkp1284lkbjg7qd2o2h4y7qduc2 FOREIGN KEY (user_creator_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE subcategory_product
    ADD CONSTRAINT fkpj76lcyc13hl4txrigmt5xvtf FOREIGN KEY (family_group_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE point_shopping
    ADD CONSTRAINT fkql4km6xy8709ua06yydfc3wcf FOREIGN KEY (user_creator) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping_project
    ADD CONSTRAINT fkqsg6dx3xmy6h9eve4fdsttnhv FOREIGN KEY (user_creator_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping
    ADD CONSTRAINT fksgm254raitk1utvdk71lpfc51 FOREIGN KEY (user_executor_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE shopping
    ADD CONSTRAINT fksighl3wexqito1ep7pcw5dsk3 FOREIGN KEY (user_creator_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE famaly_group_invitations
    ADD CONSTRAINT fku5tgt3jpifao76sx531odcal FOREIGN KEY (family_group_id) REFERENCES family_group (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

create table families(
    id uuid primary key,
    name text
);