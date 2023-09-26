package com.mycompany.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.dto.Product;
@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer>{

}
