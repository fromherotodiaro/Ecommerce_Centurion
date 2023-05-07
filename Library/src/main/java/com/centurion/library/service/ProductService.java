package com.centurion.library.service;

import com.centurion.library.dto.ProductDto;
import com.centurion.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(MultipartFile imageProduct,ProductDto productDto);
    ProductDto getById(Long id);
    void deleteById(Long id);
    void enableById(Long id);

    Page<Product> pageProduct(int PageNo);

    Page<Product> searchProduct(int PageNo ,String keyword);


}
