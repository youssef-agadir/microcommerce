package com.ecommerce.microcommerce.dao;


import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    public Product findById(int Id);
    public List<Product> findByPrixGreaterThan(int prixLimit);
    public List<Product> findByNomLike(String recherche);

    @Query("SELECT new Product(id, nom, prix,prixDachat) FROM Product p WHERE p.prix > :prixLimit")
    public List<Product> chercherUnProduitCher(@Param("prixLimit") int prix);

    @Query("SELECT new Product(id, nom, prix, prixDachat) FROM Product p order by p.nom Asc")
    public List<Product> findAllOrderByNomAsc();
}
