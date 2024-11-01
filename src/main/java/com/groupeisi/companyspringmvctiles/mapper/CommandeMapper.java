package com.groupeisi.companyspringmvctiles.mapper;

import com.groupeisi.companyspringmvctiles.dto.CommandeDto;
import com.groupeisi.companyspringmvctiles.entities.CommandeEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CommandeMapper {
    public CommandeMapper() {
    }

    public static CommandeEntity toCommandeEntity(CommandeDto commandeDto) {

        CommandeEntity commandeEntity = new CommandeEntity();

        commandeEntity.setId(commandeDto.getId());
        commandeEntity.setDate(commandeDto.getDate());
        commandeEntity.setPanier(PanierMapper.toPanierEntity(commandeDto.getPanier()));

        return commandeEntity;
    }

    public static CommandeDto toCommandeDto(CommandeEntity commandeEntity) {

        CommandeDto commandeDto = new CommandeDto();

        commandeDto.setId(commandeEntity.getId());
        commandeDto.setDate(commandeEntity.getDate());
        commandeDto.setPanier(PanierMapper.toPanierDto(commandeEntity.getPanier()));

        return commandeDto;
    }

    public static List<CommandeDto> toListCommandeDto(List<CommandeEntity> commandeEntities) {
        return commandeEntities.stream()
                .map(CommandeMapper::toCommandeDto)
                .collect(Collectors.toList());
    }

    public static List<CommandeEntity> toListCommandeEntity(List<CommandeDto> commandeDtos) {
        return commandeDtos.stream()
                .map(CommandeMapper::toCommandeEntity)
                .collect(Collectors.toList());
    }
}
