package com.groupeisi.companyspringmvctiles.dto;
import java.util.Date;

public class CommandeDto {
    private Long id;

    private Date date;

    private PanierDto panier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PanierDto getPanier() {
        return panier;
    }

    public void setPanier(PanierDto panier) {
        this.panier = panier;
    }

    public CommandeDto() {
    }

    public CommandeDto(Long id, Date date, PanierDto panier) {
        this.id = id;
        this.date = date;
        this.panier = panier;
    }
}
