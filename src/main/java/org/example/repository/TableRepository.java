package org.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TableRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createTables() {
        String profileSql = """
                create table if not exists profile (
                    id serial primary key,
                    name varchar (25) not null,
                    surname varchar (25) not null,
                    phone varchar (13) unique not null,
                    pswd varchar not null,
                    created_date timestamp default now(),
                    visible bool default true,
                    status varchar (20) not null,
                    role varchar not null
                )""";

        String cardSql = """
                create table if not exists card (
                    id serial primary key,
                    card_number varchar (16) unique,
                    exp_date date not null,
                    balance numeric not null check(balance >= 0),
                    status varchar (20) not null,
                    visible bool default true,
                    created_date timestamp not null default now()
                )""";

        String terminalSql = """
                create table if not exists terminal (
                    id serial primary key,
                    code varchar unique not null,
                    address varchar,
                    status varchar,
                    visible boolean default true,
                    created_date timestamp default now()
                )""";

        String profileCardSql = """
                create table if not exists profile_card (
                    id serial primary key,
                    card_id int not null,
                    profile_id int not null,
                    visible boolean default true,
                    created_date timestamp default now(),
                    foreign key(card_id) references card(id),
                    foreign key(profile_id) references profile(id)
                )""";

        String transactionSql = """
                create table if not exists transactions (
                    id serial primary key,
                    card_id int not null,
                    amount numeric(12, 2),
                    terminal_id int,
                    type varchar(15),
                    created_date timestamp default now(),
                    foreign key(card_id) references card(id)
                )""";

        jdbcTemplate.execute(profileSql);
        jdbcTemplate.execute(cardSql);
        jdbcTemplate.execute(terminalSql);
        jdbcTemplate.execute(profileCardSql);
        jdbcTemplate.execute(transactionSql);
    }
}
