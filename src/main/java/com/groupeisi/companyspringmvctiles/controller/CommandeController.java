package com.groupeisi.companyspringmvctiles.controller;

import com.groupeisi.companyspringmvctiles.dto.CommandeDto;
import com.groupeisi.companyspringmvctiles.dto.PanierDto;
import com.groupeisi.companyspringmvctiles.service.CommandeService;
import com.groupeisi.companyspringmvctiles.service.ICommandeService;
import com.groupeisi.companyspringmvctiles.service.IPanierService;
import com.groupeisi.companyspringmvctiles.service.PanierService;
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
public class CommandeController {
    private static final Logger logger = LoggerFactory.getLogger(CommandeController.class);

    private final ICommandeService commandeService;
    private final IPanierService panierService;

    public CommandeController(){
        this.commandeService = new CommandeService();
        this.panierService = new PanierService();
    }

    @GetMapping("/commandes")
    public String showAll(Model model) {
        logger.info(" CommandeController - Méthode GET appelée pour afficher les commandes");

        try {
            Optional<List<PanierDto>> paniers = panierService.findAll();
            model.addAttribute("panierList", paniers.orElse(new ArrayList<>()));

            Optional<List<CommandeDto>> commandes = commandeService.findAll();
            model.addAttribute("commandeList", commandes.orElse(new ArrayList<>()));

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des données", e);
        }

        return "commandes";
    }

    @GetMapping("/commandes/details/{id}")
    public String showDetails(Model model, @PathVariable Long id) {

        try {
            Optional<CommandeDto> commande = commandeService.findById(id);
            model.addAttribute("commande", commande.orElse(null));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des données", e);
        }

        return "detailsCommandes";
    }

    @PostMapping("/commandes")
    public String save(
            @RequestParam("panier_id") String panier
    ) {

        Optional<PanierDto> panierOptional = panierService.findById(Long.valueOf(panier));

        if (panierOptional.isPresent()) {
            PanierDto panierDto = panierOptional.get();

            CommandeDto commandeDto = new CommandeDto();
            commandeDto.setDate(new Date());
            commandeDto.setPanier(panierDto);
            logger.debug("Paramètres reçus : commande={}", commandeDto);

            try {
                commandeService.save(commandeDto);
                logger.info("Commande enregistré avec succès");
            } catch (Exception e) {
                logger.error("Erreur lors de l'enregistrement du commande", e);
            }
        } else {
            logger.error("Client non trouvé avec l'id ");
        }

        return "redirect:commandes";
    }

}
