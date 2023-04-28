package com.centurion.library.repository;

import com.centurion.library.model.Admin;
import com.centurion.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
