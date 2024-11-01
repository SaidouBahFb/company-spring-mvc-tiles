package com.groupeisi.companyspringmvctiles.controller;

import com.groupeisi.companyspringmvctiles.dto.ClientDto;
import com.groupeisi.companyspringmvctiles.dto.PanierDto;
import com.groupeisi.companyspringmvctiles.dto.ProductDto;
import com.groupeisi.companyspringmvctiles.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PanierController {

    private static final Logger logger = LoggerFactory.getLogger(PanierController.class);

    private final IPanierService panierService = new PanierService();

    private final IClientService clientService = new ClientService();

    private final IProductService productService = new ProductService();

    @GetMapping("/paniers")
    public String showPaniers(Model model) {
        logger.info("Affichage des paniers");

        try {
            Optional<List<PanierDto>> paniers = panierService.findAll();
            model.addAttribute("paniersList", paniers.orElse(new ArrayList<>()));

            Optional<List<ProductDto>> products = productService.findAll();
            model.addAttribute("productList", products.orElse(new ArrayList<>()));

            Optional<List<ClientDto>> clients = clientService.findAll();
            model.addAttribute("clientList", clients.orElse(new ArrayList<>()));

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des données", e);
        }

        return "paniers";
    }

    @GetMapping("/paniers/details/{id}")
    public String showDetails(Model model, @PathVariable Long id) {

        try {
            Optional<PanierDto> panier = panierService.findById(id);
            model.addAttribute("panier", panier.orElse(null));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des données", e);
        }

        return "detailsPaniers";
    }

    @PostMapping("/paniers")
    public String save(
            @RequestParam("client_id") String client,
            @RequestParam("products") List<String> products
    ) {

        logger.debug("Paramètres reçus : productRef={}, quantity={}", client, products);
        Optional<ClientDto> clientOptional = clientService.findById(Long.valueOf(client));

        if (clientOptional.isPresent()) {
            ClientDto clientDto = clientOptional.get();

            PanierDto panierDto = new PanierDto();
            panierDto.setDate(new Date());
            panierDto.setClient(clientDto);
            panierDto.setProducts(productService.findAllByRef(products).get());
            logger.debug("Paramètres reçus : panier={}", panierDto);

            try {
                panierService.save(panierDto);
                logger.info("Panier enregistré avec succès");
            } catch (Exception e) {
                logger.error("Erreur lors de l'enregistrement du panier", e);
            }
        } else {
            logger.error("Client non trouvé avec l'id ");
        }
        return "redirect:paniers";
    }
}
