package org.example.repository;

import org.example.dto.CardDTO;
import org.example.dto.TerminalDTO;
import org.example.dto.TransactionDTO;
import org.example.dto.TransactionDetailDTO;
import org.example.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
}




















