package com.centurion.library.service.impl;

import com.centurion.library.model.Category;
import com.centurion.library.repository.CategoryRepository;
import com.centurion.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
            Category categorySave = new Category(category.getName());
            return categoryRepository.save(categorySave);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = categoryRepository.findById(category.getId()).get();
            System.out.println("________get"+categoryUpdate);
            System.out.println("________no get"+categoryRepository.findById(category.getId()));
            categoryUpdate.setName(category.getName());
            categoryUpdate.set_activated(category.is_activated());
            categoryUpdate.set_deleted(category.is_deleted());
            return categoryRepository.save(categoryUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.set_deleted(true);
        category.set_activated(false);
        categoryRepository.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.set_deleted(false);
        category.set_activated(true);
        categoryRepository.save(category);
    }
}
