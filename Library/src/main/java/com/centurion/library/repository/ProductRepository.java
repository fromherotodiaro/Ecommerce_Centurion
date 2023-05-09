package com.centurion.library.repository;

import com.centurion.library.model.Category;
import com.centurion.library.model.Product;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("Select p from Product p")
    Page<Product> pageProduct(Pageable pageable);

    @Query("Select p from Product p where p.description like %?1% or p.name like %?1%"  ) /*?1 là tham số thứ nhất keyword(Tương tự ?2,?3 nếu có)*/
    Page<Product> searchProduct(String keyword, Pageable pageable);
}







