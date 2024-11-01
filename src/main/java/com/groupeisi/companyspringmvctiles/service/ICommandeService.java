package com.groupeisi.companyspringmvctiles.service;

import com.groupeisi.companyspringmvctiles.dto.CommandeDto;

import java.util.List;
import java.util.Optional;

public interface ICommandeService {
    Optional<List<CommandeDto>> findAll();

    boolean save(CommandeDto commandeDto);

    Optional<CommandeDto> findById(Long id);
}
