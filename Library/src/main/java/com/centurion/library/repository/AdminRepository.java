package com.centurion.library.repository;

import com.centurion.library.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

//    @Query(value = "query",nativeQuery = true)
    Admin  findByUsername(String username);

}
