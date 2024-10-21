package com.groupeisi.companyspringmvctiles.dao;

import com.groupeisi.companyspringmvctiles.entities.ClientEntity;

import java.util.Optional;

public interface IClientDao extends Repository<ClientEntity>{
    Optional<ClientEntity> findByEmail(String email);
    boolean update(ClientEntity clientEntity);
}
