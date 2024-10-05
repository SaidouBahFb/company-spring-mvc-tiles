package com.groupeisi.companyspringmvctiles.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.groupeisi.companyspringmvctiles.dto.ProductDto;
import com.groupeisi.companyspringmvctiles.dto.SalesDto;
import com.groupeisi.companyspringmvctiles.service.IProductService;
import com.groupeisi.companyspringmvctiles.service.ISalesService;
import com.groupeisi.companyspringmvctiles.service.ProductService;
import com.groupeisi.companyspringmvctiles.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class SalesController {
    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);

    private final ISalesService salesService = new SalesService();
    private final IProductService productService = new ProductService();

    @GetMapping("/sales")
    public String showSales(Model model) {
        logger.info("SalesController - showSales");

        try {
            Optional<List<SalesDto>> sales = salesService.findAll();
            Optional<List<ProductDto>> products = productService.findAll();

            if (sales.isPresent()) {
                logger.info("Liste des ventes récupérée avec succès");
                model.addAttribute("salesList", sales.get());
            } else {
                logger.info("Aucune vente trouvée");
                model.addAttribute("salesList", new ArrayList<SalesDto>());
            }

            if (products.isPresent()) {
                logger.info("Liste des produits récupérée avec succès");
                model.addAttribute("productList", products.get());
            } else {
                logger.info("Aucun produit trouvé");
                model.addAttribute("productList", new ArrayList<ProductDto>());
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la liste des ventes", e);
        }

        return "sales";
    }

    @PostMapping("/sales")
    public String saveSale(
            @RequestParam("productRef") String productRef,
            @RequestParam("quantity") double quantity) {
        logger.info("SalesController - saveSale");

        logger.debug("Paramètres reçus : productRef={}, quantity={}", productRef, quantity);

        ProductDto productDto = new ProductDto();

        productDto.setRef(productRef);
        SalesDto salesDto = new SalesDto();
        salesDto.setQuantity(quantity);
        salesDto.setProduct(productDto);
        salesDto.setDateP(new Date());

        try {
            salesService.save(salesDto);
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement de la vente", e);
        }

        return "redirect:/sales";
    }
}

