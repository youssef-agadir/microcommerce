package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.web.exceptions.ProduitInvalidException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api( description ="API pour les opérations CRUD sur les produits.")
@RestController
public class ProductsController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/Produits",method= RequestMethod.GET)
    public MappingJacksonValue listeProduits(){
        List<Product> productList = productDao.findAll();

        SimpleBeanPropertyFilter monfilter = SimpleBeanPropertyFilter.serializeAllExcept("prixDachat");
        FilterProvider listDoNosFilter = new SimpleFilterProvider().addFilter("monFiltreDynamique",monfilter);

        MappingJacksonValue prduitsFiltres = new MappingJacksonValue(productList);

        prduitsFiltres.setFilters(listDoNosFilter);

        return prduitsFiltres;
    }

    @GetMapping(value = "/Produits/trier")
    public List<Product> trierProduitsParOrdreAlphabetique(){
        return productDao.findAllOrderByNomAsc();
    }

    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduits(@PathVariable int id) throws ProduitIntrouvableException {
        Product produit = productDao.findById(id);

        if(produit == null)
            throw new ProduitIntrouvableException("le produit "+id+" est introuvable");

        return produit;
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) throws ProduitInvalidException {
        if (product.getPrix() == 0)
            throw new ProduitInvalidException("le prix de produit égal à zéro");

        Product productAdded = productDao.save(product);

        if (product == null){
            return ResponseEntity.noContent().build();
        }else {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(productAdded.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @GetMapping(value = "test/produits/{prixLimit}")
    public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
        //return productDao.findByNomLike("%"+recherche+"%");
        return productDao.chercherUnProduitCher(prixLimit);
        //return productDao.findByPrixGreaterThan(prixLimit);
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id){
        productDao.deleteById(id);
    }

    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product){

        productDao.save(product);
    }

    @GetMapping (value = "/Produits/CalculerMargeProduit/{id}")
    public int  calculerMargeProduit(@PathVariable int id){
        int prixProduit;
        int prixDachatProduit;

        Product produit = productDao.findById(id);

        prixProduit = produit.getPrix();
        prixDachatProduit = produit.getPrixDachat();

        int marge = prixProduit - prixDachatProduit;
        return marge;
    }



}
