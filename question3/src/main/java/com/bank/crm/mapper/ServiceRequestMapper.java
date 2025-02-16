package com.bank.crm.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bank.crm.dto.ServiceRequestDTO;
import com.bank.crm.entity.ServiceRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceRequestMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ServiceRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ServiceRequestDTO toDTO(ServiceRequest entity) {
        return modelMapper.map(entity, ServiceRequestDTO.class);
    }

    public ServiceRequest toEntity(ServiceRequestDTO dto) {
        return modelMapper.map(dto, ServiceRequest.class);
    }

    public List<ServiceRequestDTO> toDTOList(List<ServiceRequest> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceRequest> toEntityList(List<ServiceRequestDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}