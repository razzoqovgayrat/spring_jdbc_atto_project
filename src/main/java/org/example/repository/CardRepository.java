package org.example.repository;

import org.example.dto.CardDTO;
import org.example.enums.GeneralStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public CardDTO getCardByNumber(String cardNumber) {
        String sql = "select * from card where card_number = ?";
        List<CardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDTO.class), cardNumber);
        if (list.isEmpty()) return null;
        return list.getFirst();
    }

    public int save(CardDTO cardDTO) {
        String sql = "insert into card(card_number, exp_date, balance, status, created_date) values (?, ?, ?, ?, ?)";

        PreparedStatementSetter preparedStatementSetter = ps -> {
            ps.setString(1, cardDTO.getCardNumber());
            ps.setDate(2, Date.valueOf(cardDTO.getExpDate()));
            ps.setDouble(3, cardDTO.getBalance());
            ps.setString(4, cardDTO.getStatus().toString());
            ps.setTimestamp(5, Timestamp.valueOf(cardDTO.getCreatedDate()));
        };

        return jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public List<CardDTO> getList() {
        String sql = "select * from card where visible = true";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CardDTO.class));
    }

    public int updateCard(String cardNumber, LocalDate newExpDate) {
        String sql = "update card set exp_date = ? where card_number = ?";
        return jdbcTemplate.update(sql, Date.valueOf(newExpDate), cardNumber);
    }

    public void changeCardStatus(String cardNumber, GeneralStatus generalStatus) {
        String sql = "update card set status = ? where card_number = ?";
        jdbcTemplate.update(sql, generalStatus.name(), cardNumber);
    }

    public int deleteCard(String cardNumber) {
        String sql = "update card set visible = false where card_number = ?";
        return jdbcTemplate.update(sql, cardNumber);
    }
}
