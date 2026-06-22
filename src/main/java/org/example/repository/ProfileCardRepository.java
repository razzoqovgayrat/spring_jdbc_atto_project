package org.example.repository;

import org.example.dto.CardDTO;
import org.example.dto.ProfileCardDTO;
import org.example.enums.GeneralStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileCardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProfileCardDTO getProfileCardByCardId(int cardId) {
        String sql = "select * from profile_card where card_id = ? and visible = true";
        List<ProfileCardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileCardDTO.class), cardId);
        if (list.isEmpty()) return null;
        return list.getFirst();
    }

    public int save(ProfileCardDTO dto) {
        String sql = "insert into profile_card (card_id, profile_id) values (?, ?)";
        return jdbcTemplate.update(sql, dto.getCardId(), dto.getProfileId());
    }

    public List<ProfileCardDTO> getProfileCardListByProfileId(int profileId) {
        String sql = "select card_number, exp_date, balance, status, pc.created_date " +
                "from profile_card as pc " +
                "inner join card as c on c.id = pc.card_id " +
                "where profile_id = ? and pc.visible = true";

        RowMapper<ProfileCardDTO> rowMapper = (rs, rowNum) -> {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setCardNumber(rs.getString("card_number"));
            cardDTO.setExpDate(rs.getDate("exp_date").toLocalDate());
            cardDTO.setBalance(rs.getDouble("balance"));
            cardDTO.setStatus(GeneralStatus.valueOf(rs.getString("status")));

            ProfileCardDTO dto = new ProfileCardDTO();
            dto.setCard(cardDTO);
            dto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            return dto;
        };

        return jdbcTemplate.query(sql, rowMapper, profileId);
    }
}
