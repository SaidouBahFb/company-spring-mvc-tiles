package com.groupeisi.companyspringmvctiles.mapper;

import com.groupeisi.companyspringmvctiles.dto.ClientDto;
import com.groupeisi.companyspringmvctiles.entities.ClientEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public ClientMapper() {
    }

    public static ClientEntity toClientEntity(ClientDto clientDto) {

        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setId(clientDto.getId());
        clientEntity.setEmail(clientDto.getEmail());
        clientEntity.setPassword(clientDto.getPassword());
        clientEntity.setFirstName((clientDto.getFirstName()));
        clientEntity.setLastName((clientDto.getLastName()));
        clientEntity.setTel((clientDto.getTel()));

        return clientEntity;
    }

    public static ClientDto toClientDto(ClientEntity clientEntity) {

        ClientDto clientDto = new ClientDto();

        clientDto.setId(clientEntity.getId());
        clientDto.setEmail(clientEntity.getEmail());
        clientDto.setPassword(clientEntity.getPassword());
        clientDto.setFirstName(clientEntity.getFirstName());
        clientDto.setLastName(clientEntity.getLastName());
        clientDto.setTel(clientEntity.getTel());

        return clientDto;
    }

    public static List<ClientDto> toListClientDto(List<ClientEntity> clientEntities) {
        return clientEntities.stream()
                .map(ClientMapper::toClientDto)
                .collect(Collectors.toList());
    }
}
