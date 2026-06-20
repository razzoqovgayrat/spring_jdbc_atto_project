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
        jdbcTemplate.execute(profileSql);
    }
}
