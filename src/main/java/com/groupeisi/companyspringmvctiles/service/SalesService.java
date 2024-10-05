package com.groupeisi.companyspringmvctiles.service;

import java.util.List;
import java.util.Optional;

import com.groupeisi.companyspringmvctiles.dao.IProductDao;
import com.groupeisi.companyspringmvctiles.dao.ISalesDao;
import com.groupeisi.companyspringmvctiles.dao.ProductDao;
import com.groupeisi.companyspringmvctiles.dao.SalesDao;
import com.groupeisi.companyspringmvctiles.dto.ProductDto;
import com.groupeisi.companyspringmvctiles.dto.SalesDto;
import com.groupeisi.companyspringmvctiles.entities.ProductEntity;
import com.groupeisi.companyspringmvctiles.entities.Sales;
import com.groupeisi.companyspringmvctiles.exception.NotAvailableQuantityException;
import com.groupeisi.companyspringmvctiles.mapper.ProductMapper;
import com.groupeisi.companyspringmvctiles.mapper.SalesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;


public class SalesService implements ISalesService {

    private static final Logger logger = LoggerFactory.getLogger(SalesService.class);
    private ISalesDao salesDao = new SalesDao();
    private IProductService productService = new ProductService();
    private final IProductDao productDao;

    public SalesService() {
        this.productDao = new ProductDao();
    }

    @Override
    public Optional<List<SalesDto>> findAll() {
        List<Sales> salesEntities = salesDao.list(new Sales());
        List<SalesDto> salesDtos = SalesMapper.toListSalesDto(salesEntities);

        return Optional.of(salesDtos);
    }

    @Override
    public boolean save(SalesDto salesDto) {

        logger.info("SalesService - Tentative d'enregistrement d'une vente : {}", salesDto);

        Sales salesEntity = SalesMapper.toSalesEntity(salesDto);
        Optional<ProductEntity> product = productDao.findByRef(salesDto.getProduct().getRef());

        try {

            if (product.isPresent()) {
                ProductEntity productEntity = product.get();
                double newStockValue = productEntity.getStock() - salesDto.getQuantity();
                productEntity.setStock(newStockValue);
                boolean productUpdated = productDao.update(productEntity);
                salesEntity.setProduct(productEntity);
                boolean salesSaved = salesDao.save(salesEntity);
                logger.info("SalesService - Enregistrement de la vente réussie : {}", salesSaved);

                logger.info("SalesService - Mise à jour du produit réussie : {}", productUpdated);
                if (!productUpdated) {
                    logger.warn("PurchasesService - Échec de la mise à jour du stock pour le produit avec ref : {}");
                    return false;
                }
            }

        } catch (Exception e) {
            logger.error("SalesService - Erreur lors de l'enregistrement de la vente", e);
            return false;
        }
        return true;
    }


    @Transactional
    @Override
    public boolean saleTransactional(Sales sales) {
        try {
            Optional<ProductEntity> productOptional = this.productDao.findByRef(sales.getProduct().getRef());
            if (productOptional.isPresent()) {
                ProductEntity product = productOptional.get();
                if (product.getStock() > sales.getQuantity() ) {
                    var quantity = product.getStock() - sales.getQuantity();
                    product.setStock(quantity);
                    salesDao.save(sales);
                    this.productDao.update(product);
                }else {
                    throw new NotAvailableQuantityException("La quantité n'est pas disponible");
                }
            }else {
                logger.info("Le produit n'existe pas");
            }
        }catch (Exception e) {
            logger.error("Erreur :", e);
        }
        return false;
    }

}
