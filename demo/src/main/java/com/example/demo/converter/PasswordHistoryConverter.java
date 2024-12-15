package com.example.demo.converter;

import com.example.demo.dto.PasswordHistoryDTO;
import com.example.demo.entity.PasswordHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PasswordHistoryConverter {
    private final ModelMapper modelMapper;

    public PasswordHistoryConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PasswordHistoryDTO convertToDTO(PasswordHistory passwordHistory) {
        return modelMapper.map(passwordHistory, PasswordHistoryDTO.class);
    }
    public PasswordHistory convertToEntity(PasswordHistoryDTO passwordHistoryDTO) {
        return modelMapper.map(passwordHistoryDTO, PasswordHistory.class);
    }
}
