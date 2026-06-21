package org.example.service;

import org.example.dto.TerminalDTO;
import org.example.enums.GeneralStatus;
import org.example.repository.TerminalRepository;
import org.example.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    public void addTerminal(String code, String address) {
        TerminalDTO terminalByCode = terminalRepository.getTerminalByCode(code);
        if (terminalByCode != null) {
            System.out.println("Terminal already exists");
            return;
        }

        TerminalDTO terminalDTO = new TerminalDTO();
        terminalDTO.setCode(code);
        terminalDTO.setAddress(address);
        terminalDTO.setStatus(GeneralStatus.ACTIVE);
        terminalDTO.setCreatedDate(LocalDateTime.now());

        if (terminalRepository.save(terminalDTO) == 1) {
            System.out.println("successfully saved");
        }
    }

    public void terminalList() {
        List<TerminalDTO> terminalDTOList = terminalRepository.getList();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("|                               Terminal List                                |");
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-10s | %-22s | %-8s | %-18s |%n", "Id", "Code", "Address", "Status", "CreatedDate");
        System.out.println("------------------------------------------------------------------------------");
        terminalDTOList.forEach(terminalDTO -> {
            String createdDate = DateUtil.toSimpleFormat(terminalDTO.getCreatedDate());
            System.out.printf("| %-4d | %-10s | %-22s | %-8s | %-18s |%n", terminalDTO.getId(), terminalDTO.getCode(), terminalDTO.getAddress(), terminalDTO.getStatus(), createdDate);
        });
        System.out.println("------------------------------------------------------------------------------");

    }

    public void updateTerminal(String code, String newAddress) {
        TerminalDTO terminalByCode = terminalRepository.getTerminalByCode(code);
        if (terminalByCode == null) {
            System.out.println("terminal not found");
            return;
        }

        if (terminalRepository.updateTerminal(code, newAddress) == 1) {
            System.out.println("successfully updated");
        }
    }

    public void changeTerminalStatus(String code) {
        TerminalDTO terminalByCode = terminalRepository.getTerminalByCode(code);
        if (terminalByCode == null) {
            System.out.println("card not found");
            return;
        }

        int n = 0;
        if (terminalByCode.getStatus().equals(GeneralStatus.ACTIVE)) {
            n = terminalRepository.changeTerminalStatus(code, GeneralStatus.BLOCK);
        } else {
            n = terminalRepository.changeTerminalStatus(code, GeneralStatus.ACTIVE);
        }

        if (n == 1) {
            System.out.println("terminal status changed");
        } else {
            System.out.println("error");
        }
    }

    public void deleteTerminal(String code) {
        TerminalDTO terminalByCode = terminalRepository.getTerminalByCode(code);
        if (terminalByCode == null) {
            System.out.println("card not found");
            return;
        }

        if (!terminalByCode.isVisible()) {
            System.out.println("terminal already deleted");
            return;
        }

        if (terminalRepository.deleteTerminal(code) == 1) {
            System.out.println("terminal deled");
        } else {
            System.out.println("error");
        }
    }
}
