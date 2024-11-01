package com.groupeisi.companyspringmvctiles.service;

import com.groupeisi.companyspringmvctiles.dao.*;
import com.groupeisi.companyspringmvctiles.dto.PanierDto;
import com.groupeisi.companyspringmvctiles.dto.ProductDto;
import com.groupeisi.companyspringmvctiles.entities.PanierEntity;
import com.groupeisi.companyspringmvctiles.entities.ClientEntity;
import com.groupeisi.companyspringmvctiles.entities.ProductEntity;
import com.groupeisi.companyspringmvctiles.mapper.PanierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierService implements IPanierService {

    private static final Logger logger = LoggerFactory.getLogger(PanierService.class);

    private IPanierDao panierDao = new PanierDao();

    @Override
    public Optional<List<PanierDto>> findAll() {
        logger.info(" - Tentative de liste des paniers");

        List<PanierEntity> panierEntities = panierDao.list(new PanierEntity());
        List<PanierDto> panierDtos = PanierMapper.toListPanierDto(panierEntities);
        return Optional.of(panierDtos);
    }

    @Override
    public boolean save(PanierDto panierDto) {
        PanierEntity panierEntity = PanierMapper.toPanierEntity(panierDto);
        return panierDao.save(panierEntity);
    }

    @Override
    public Optional<PanierDto> findById(Long id) {
        PanierEntity panier = new PanierEntity();
        Optional<PanierEntity> panierEntity = Optional.ofNullable(panierDao.get(id, panier));
        return panierEntity.map(PanierMapper::toPanierDto);
    }

}
