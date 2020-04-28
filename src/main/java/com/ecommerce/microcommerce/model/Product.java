package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@JsonFilter("monFiltreDynamique")
@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;
    @Length(min = 3,max = 20,message = "Nom trop long ou trop court. Et oui messages sont plus stylés que ceux de Spring")
    private String nom;
    private int prix;
    private int prixDachat;


    public Product(int id, String nom, int prix, int prixDachat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixDachat = prixDachat;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixDachat() {
        return prixDachat;
    }

    public void setPrixDachat(int prixDachat) {
        this.prixDachat = prixDachat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", prixDachat=" + prixDachat +
                '}';
    }
}
