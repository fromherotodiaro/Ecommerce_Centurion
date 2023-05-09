package com.centurion.admin.controller;

import com.centurion.library.dto.ProductDto;
import com.centurion.library.model.Category;
import com.centurion.library.model.Product;
import com.centurion.library.service.CategoryService;
import com.centurion.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }

        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("title","Manage Product");
        model.addAttribute("products",productDtoList);
        model.addAttribute("size",productDtoList.size());

        return "products";
    }

    @GetMapping("/search-result/{pageNo}/{sortField}/{sortDir}")
    public String searchProducts(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 @PathVariable("sortField") String sortField,
                                 @PathVariable("sortDir") String sortDir,
                                 Model model,
                                 Principal principal ){
        if (principal == null){
            return "redirect:/login";
        }

        if (sortField == null && sortDir == null){
            sortField="id";
            sortDir="asc";
        }

        Page<Product> products = productService.searchProduct(pageNo,keyword,sortField,sortDir);
        model.addAttribute("title","Search Result");
        model.addAttribute("products",products);
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalItems",products.getTotalElements());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        //Sort
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "result-products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @GetMapping("/products/{pageNo}/{sortField}/{sortDir}")
    public String productsPage(Model model,
                               @PathVariable("pageNo") int pageNo,
                               @PathVariable("sortField") String sortField,
                               @PathVariable("sortDir") String sortDir,

                               Principal principal){
        if (principal == null){
            return "redirect:/login";
        }

        Page<Product> products = productService.pageProduct(pageNo,sortField,sortDir);
        model.addAttribute("title","Manage Product");
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalItems",products.getTotalElements());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("products",products);
        //Sort
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "products";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product") ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try {
            productService.save(imageProduct,productDto);
            attributes.addFlashAttribute("success","Add seccessfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Failed to add!");
        }
        return "redirect:/products";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Update products");
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id
            ,@ModelAttribute("productDto") ProductDto productDto,
             @RequestParam("imageProduct") MultipartFile imageProduct,
             RedirectAttributes attributes ){
        try {
            productService.update(imageProduct,productDto);
            attributes.addFlashAttribute("success","Update Successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Failed to add!");
        }

        return "redirect:/products";
    }

    @RequestMapping(value = "/enable-product/{id}", method ={RequestMethod.PUT, RequestMethod.GET})
    public String enableProduct(@PathVariable("id") Long id,RedirectAttributes attributes){
        try {

            productService.enableById(id);
            attributes.addFlashAttribute("success","Enable successfully!");

        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Failed to enabled!");
        }

        return "redirect:/products";
    }

    @RequestMapping(value = "/delete-product/{id}", method ={RequestMethod.PUT, RequestMethod.GET})
    public String deleteProduct(@PathVariable("id") Long id,RedirectAttributes attributes) {
        try {

            productService.deleteById(id);
            attributes.addFlashAttribute("success","Deleted successfully!");

        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Failed to deleted!");
        }
        return "redirect:/products";
    }
}
