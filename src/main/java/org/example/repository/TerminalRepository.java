package org.example.repository;

import org.example.dto.TerminalDTO;
import org.example.enums.GeneralStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TerminalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public TerminalDTO getTerminalByCode(String code) {
        String sql = "select * from terminal where code = ?";
        List<TerminalDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TerminalDTO.class), code);
        if (list.isEmpty()) return null;
        return list.getFirst();
    }

    public int save(TerminalDTO terminalDTO) {
        String sql = "insert into terminal (code, address, status, created_date) values (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, terminalDTO.getCode(), terminalDTO.getAddress(),
                terminalDTO.getStatus().toString(), Timestamp.valueOf(terminalDTO.getCreatedDate()));
    }

    public List<TerminalDTO> getList() {
        String sql = "select * from terminal where visible = true";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TerminalDTO.class));
    }

    public int updateTerminal(String code, String newAddress) {
        String sql = "update terminal set address = ? where code = ?";
        return jdbcTemplate.update(sql, newAddress, code);
    }

    public int changeTerminalStatus(String code, GeneralStatus generalStatus) {
        String sql = "update terminal set status = ? where code = ?";
        return jdbcTemplate.update(sql, generalStatus.toString(), code);
    }

    public int deleteTerminal(String code) {
        String sql = "update terminal set visible = false where code = ?";
        return jdbcTemplate.update(sql, code);
    }
}
