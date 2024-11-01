package com.groupeisi.companyspringmvctiles.mapper;

import com.groupeisi.companyspringmvctiles.dto.PanierDto;
import com.groupeisi.companyspringmvctiles.entities.PanierEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PanierMapper {
    public PanierMapper() {
    }

    public static PanierEntity toPanierEntity(PanierDto panierDto) {

        PanierEntity panierEntity = new PanierEntity();

        panierEntity.setId(panierDto.getId());
        panierEntity.setDate(panierDto.getDate());
        panierEntity.setClient(ClientMapper.toClientEntity(panierDto.getClient()));
        panierEntity.setProducts(ProductMapper.toListProductEntity(panierDto.getProducts()));

        return panierEntity;
    }

    public static PanierDto toPanierDto(PanierEntity panierEntity) {

        PanierDto panierDto = new PanierDto();

        panierDto.setId(panierEntity.getId());
        panierDto.setClient(ClientMapper.toClientDto(panierEntity.getClient()));
        panierDto.setDate(panierEntity.getDate());
        panierDto.setProducts(ProductMapper.toListProductDto(panierEntity.getProducts()));

        return panierDto;
    }

    public static List<PanierDto> toListPanierDto(List<PanierEntity> panierEntities) {
        return panierEntities.stream()
                .map(PanierMapper::toPanierDto)
                .collect(Collectors.toList());
    }

    public static List<PanierEntity> toListPanierEntity(List<PanierDto> panierDtos) {
        return panierDtos.stream()
                .map(PanierMapper::toPanierEntity)
                .collect(Collectors.toList());
    }
}
