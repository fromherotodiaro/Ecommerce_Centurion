package com.centurion.library.service.impl;

import com.centurion.library.dto.ProductDto;
import com.centurion.library.model.Category;
import com.centurion.library.model.Product;
import com.centurion.library.repository.ProductRepository;
import com.centurion.library.service.ProductService;
import com.centurion.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> products = productRepository.findAll();

        for (Product product: products) {
            ProductDto productDto = new ProductDto();

            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setImage(product.getImage());
            productDto.setCategory(product.getCategory());
            productDto.setDeleted(product.isDeleted());
            productDto.setActivated(product.isActivated());
            productDtoList.add(productDto);
        }

        return productDtoList;
    }

    @Override
    public  Product save(MultipartFile imageProduct, ProductDto productDto) {

        try
        {
//            String fileName = StringUtils.cleanPath(imageProduct.getOriginalFilename());
//            if(fileName.contains(".."))
//            {
//                System.out.println("not a a valid file");
//            }

            Product product = new Product();
            if (imageProduct == null || imageProduct.isEmpty()){
                product.setImage(null);
            }else{
                if (imageUpload.uploadImage(imageProduct)){
                    System.out.println("Upload successfully");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
            product.setDeleted(false);
            product.setActivated(true);
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.getReferenceById(productDto.getId());

            if (imageProduct == null || imageProduct.isEmpty()){
                product.setImage(product.getImage());
            }else {
                if (imageUpload.checkExisted(imageProduct) == false){
                    System.out.println("Upload to folder");
                    imageUpload.uploadImage(imageProduct);

                }
                System.out.println("Image exister");
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCostPrice(productDto.getCostPrice());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCategory(productDto.getCategory());
//            product.setDeleted(false);
//            product.setActivated(true);

            System.out.println(product.getName()+"___"+product.getCategory());

//            return product;
            return productRepository.save(product);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public ProductDto getById(Long id) {

        Product product = productRepository.getReferenceById(id);
        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        productDto.setDeleted(false);
        productDto.setActivated(true);

        return productDto;
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setActivated(false);
        product.setDeleted(true);

        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setActivated(true);
        product.setDeleted(false);
        productRepository.save(product);
    }

    @Override
    public Page<Product> pageProduct(int PageNo) {
        Pageable pageable = PageRequest.of(PageNo,1);
        Page<Product> productPage = productRepository.pageProduct(pageable);

        return productPage;
    }

    @Override
    public Page<Product> searchProduct(int PageNo ,String keyword) {

        Pageable pageable = PageRequest.of(PageNo,5);

        Page<Product> products = productRepository.searchProduct(keyword,pageable);

        return products;
    }




}



















