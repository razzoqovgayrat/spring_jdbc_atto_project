package org.example.repository;

import org.example.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ProfileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int create(ProfileDTO dto) {
        String sql = "insert into profile(name, surname, phone, pswd, created_date, visible, status, role) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatementSetter preparedStatementSetter = ps -> {
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getSurname());
            ps.setString(3, dto.getPhone());
            ps.setString(4, dto.getPswd());
            ps.setTimestamp(5, Timestamp.valueOf(dto.getCreatedDate()));
            ps.setBoolean(6, dto.isVisible());
            ps.setString(7, dto.getStatus().toString());
            ps.setString(8, dto.getRole().toString());
        };

        return jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public ProfileDTO getByPhone(String phone) {
        String sql = "select * from profile where phone = ?";
        List<ProfileDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), phone);
        if (!list.isEmpty()) return list.getFirst();
        return null;
    }
}
