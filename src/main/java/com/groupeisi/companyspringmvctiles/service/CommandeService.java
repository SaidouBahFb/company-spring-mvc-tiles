package com.groupeisi.companyspringmvctiles.service;

import com.groupeisi.companyspringmvctiles.dao.ICommandeDao;
import com.groupeisi.companyspringmvctiles.dao.CommandeDao;
import com.groupeisi.companyspringmvctiles.dto.CommandeDto;
import com.groupeisi.companyspringmvctiles.entities.CommandeEntity;
import com.groupeisi.companyspringmvctiles.mapper.CommandeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CommandeService implements ICommandeService{
    private static final Logger logger = LoggerFactory.getLogger(CommandeService.class);

    private ICommandeDao commandeDao = new CommandeDao();

    @Override
    public Optional<List<CommandeDto>> findAll() {
        logger.info(" CommandeService - Tentative de liste des commandes");

        List<CommandeEntity> commandeEntities = commandeDao.list(new CommandeEntity());
        List<CommandeDto> commandeDtos = CommandeMapper.toListCommandeDto(commandeEntities);
        return Optional.of(commandeDtos);
    }

    @Override
    public boolean save(CommandeDto commandeDto) {
        CommandeEntity commandeEntity = CommandeMapper.toCommandeEntity(commandeDto);
        return commandeDao.save(commandeEntity);
    }

    @Override
    public Optional<CommandeDto> findById(Long id) {
        CommandeEntity commande = new CommandeEntity();
        Optional<CommandeEntity> commandeEntity = Optional.ofNullable(commandeDao.get(id, commande));
        return commandeEntity.map(CommandeMapper::toCommandeDto);
    }
}
