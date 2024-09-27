package com.groupeisi.companyspringmvctiles.service;

import java.util.List;
import java.util.Optional;

import com.groupeisi.companyspringmvctiles.dao.IPurchasesDao;
import com.groupeisi.companyspringmvctiles.dao.PurchasesDao;
import com.groupeisi.companyspringmvctiles.dto.ProductDto;
import com.groupeisi.companyspringmvctiles.dto.PurchasesDto;
import com.groupeisi.companyspringmvctiles.entities.Purchases;
import com.groupeisi.companyspringmvctiles.mapper.PurchasesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PurchasesService implements IPurchasesService {

    private static final Logger logger = LoggerFactory.getLogger(PurchasesService.class);

    private IPurchasesDao purchasesDao = new PurchasesDao();
    private IProductService productService = new ProductService();

    @Override
    public Optional<List<PurchasesDto>> findAll() {
        List<Purchases> purchasesEntities = purchasesDao.list(new Purchases());
        List<PurchasesDto> purchasesDtos = PurchasesMapper.toListPurchasesDto(purchasesEntities);

        return Optional.of(purchasesDtos);
    }

    @Override
    public boolean save(PurchasesDto purchasesDto) {
        logger.info("PurchasesService - Tentative d'enregistrement d'un achat : {}", purchasesDto);

        Purchases purchasesEntity = PurchasesMapper.toPurchasesEntity(purchasesDto);
        ProductDto productDto = purchasesDto.getProduct();

        try {

            double newStockValue = productDto.getStock() + purchasesDto.getQuantity();
            logger.debug("PurchasesService - Calcul de la nouvelle valeur de stock : {}", newStockValue);

            productDto.setStock(newStockValue);

            boolean productUpdated = productService.update(productDto);
            logger.info("PurchasesService - Mise à jour du produit réussie : {}", productUpdated);

            if (!productUpdated) {
                logger.warn("PurchasesService - Échec de la mise à jour du stock pour le produit avec ref : {}", productDto.getRef());
                return false;
            }

            boolean purchaseSaved = purchasesDao.save(purchasesEntity);
            logger.info("PurchasesService - Enregistrement de l'achat réussi : {}", purchaseSaved);

            return purchaseSaved;
        } catch (Exception e) {
            logger.error("PurchasesService - Erreur lors de l'enregistrement de l'achat", e);
            return false;
        }
    }
}
