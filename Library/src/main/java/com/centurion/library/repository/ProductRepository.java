package com.centurion.library.repository;

import com.centurion.library.model.Category;
import com.centurion.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {



}
