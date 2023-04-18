package com.centurion.library.service;

import com.centurion.library.dto.AdminDto;
import com.centurion.library.model.Admin;

public interface AdminService {

    Admin findByUsername(String name);

    Admin save(AdminDto adminDto);
}
