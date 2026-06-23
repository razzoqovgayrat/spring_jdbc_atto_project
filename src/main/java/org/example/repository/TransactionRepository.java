package org.example.repository;

import org.example.dto.*;
import org.example.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int createTransaction(TransactionDTO transactionDTO) {
        String sql = "insert into transactions(card_id, terminal_id, amount, type) values (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, transactionDTO.getCardId(), transactionDTO.getTerminalId(),
                transactionDTO.getAmount(), transactionDTO.getTransactionType().toString());
    }

    public List<TransactionDetailDTO> getTransactionListByProfileId(int profileId) {
        String sql = """
                select c.card_number, ter.address, t.amount, t.created_date, t.type from transactions t
                    inner join card as c on c.id = t.card_id
                    left join terminal as ter on t.terminal_id = ter.id
                         where t.card_id in (select card_id from profile_card where profile_id = ?)
                         order by t.created_date desc""";

        RowMapper<TransactionDetailDTO> rowMapper = (rs, rowNum) -> {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setCardNumber(rs.getString("card_number"));

            TerminalDTO terminalDTO = new TerminalDTO();
            terminalDTO.setAddress(rs.getString("address"));

            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(rs.getDouble("amount"));
            transactionDTO.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            transactionDTO.setTransactionType(TransactionType.valueOf(rs.getString("type")));

            TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
            transactionDetailDTO.setTransaction(transactionDTO);
            transactionDetailDTO.setCard(cardDTO);
            transactionDetailDTO.setTerminal(terminalDTO);

            return transactionDetailDTO;
        };

        return jdbcTemplate.query(sql, rowMapper, profileId);
    }

    public List<TransactionDetailDTO> getAll() {
        String sql = """
                select c.card_number, p.name, p.surname, ter.code, ter.address,
                t.amount, t.created_date, t.type
                from transactions as t
                inner join card as c on c.id = t.card_id
                left join terminal as ter on ter.id = t.terminal_id
                left join profile_card as pc on pc.card_id = t.card_id
                inner join profile as p on p.id = pc.profile_id
                order by t.created_date desc;
                """;
        RowMapper<TransactionDetailDTO> rowMapper = (rs, rowNum) -> {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setCardNumber(rs.getString("card_number"));

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(rs.getString("name"));
            profileDTO.setSurname(rs.getString("surname"));

            TerminalDTO terminalDTO = new TerminalDTO();
            terminalDTO.setCode(rs.getString("code"));
            terminalDTO.setAddress(rs.getString("address"));

            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(rs.getDouble("amount"));
            transactionDTO.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            transactionDTO.setTransactionType(TransactionType.valueOf(rs.getString("type")));

            TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
            transactionDetailDTO.setCard(cardDTO);
            transactionDetailDTO.setProfile(profileDTO);
            transactionDetailDTO.setTerminal(terminalDTO);
            transactionDetailDTO.setTransaction(transactionDTO);

            return transactionDetailDTO;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<TransactionDetailDTO> paymentByDate(LocalDate date) {
        String sql = """
                select c.card_number, ter.address, t.amount, t.created_date, t.type
                         from transactions t
                         inner join card c on t.card_id = c.id
                         inner join terminal ter on ter.id = t.terminal_id
                         where date(t.created_date) = ? and t.type = 'PAYMENT'
                order by t.created_date desc""";
        return jdbcTemplate.query(sql, getRowMapper(), Date.valueOf(date));
    }

    public List<TransactionDetailDTO> transactionBetweenDays(LocalDate fromDate, LocalDate toDate) {
        String sql = """
                select c.card_number, ter.address, t.amount, t.created_date, t.type
                         from transactions t
                         inner join card c on t.card_id = c.id
                         inner join terminal ter on ter.id = t.terminal_id
                         where date(t.created_date) between ? and ? and t.type = 'PAYMENT'
                order by t.created_date desc""";

        return jdbcTemplate.query(sql, getRowMapper(), Date.valueOf(fromDate), Date.valueOf(toDate));
    }

    public List<TransactionDetailDTO> transactionByTerminalCode(String code) {
        String sql = """
                select c.card_number, ter.address, t.amount, t.created_date, t.type
                         from transactions t
                         inner join card c on t.card_id = c.id
                         inner join terminal ter on ter.id = t.terminal_id
                         where ter.code = ? and t.type = 'PAYMENT'
                order by t.created_date desc""";

        return jdbcTemplate.query(sql, getRowMapper(), code);
    }

    public List<TransactionDetailDTO> transactionListByCardId(int cardId) {
        String sql = """
                select c.card_number, ter.address, t.amount, t.created_date, t.type
                         from transactions t
                         inner join card c on t.card_id = c.id
                         inner join terminal ter on ter.id = t.terminal_id
                         where t.card_id = ? and t.type = 'PAYMENT'
                order by c.created_date desc""";

        return jdbcTemplate.query(sql, getRowMapper(), cardId);
    }

    private RowMapper<TransactionDetailDTO> getRowMapper() {
        RowMapper<TransactionDetailDTO> rowMapper = (rs, rowNum) -> {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setCardNumber(rs.getString("card_number"));

            TerminalDTO terminalDTO = new TerminalDTO();
            terminalDTO.setAddress(rs.getString("address"));

            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(rs.getDouble("amount"));
            transactionDTO.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            transactionDTO.setTransactionType(TransactionType.valueOf(rs.getString("type")));

            TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO();
            transactionDetailDTO.setCard(cardDTO);
            transactionDetailDTO.setTerminal(terminalDTO);
            transactionDetailDTO.setTransaction(transactionDTO);

            return transactionDetailDTO;
        };
        return rowMapper;
    }
}




















