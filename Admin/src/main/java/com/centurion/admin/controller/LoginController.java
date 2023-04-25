package com.centurion.admin.controller;

import com.centurion.library.dto.AdminDto;
import com.centurion.library.model.Admin;
import com.centurion.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AdminServiceImpl adminService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("title","Login");
        return "login";
    }

    @GetMapping("/admin-home")
    public String home(Model model){
        model.addAttribute("title","Home Page");
        return "admin-home";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title","Register");
        model.addAttribute("adminDto",new AdminDto());
        return "register";
    }



    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("title","Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin( @ModelAttribute("adminDto") @Valid AdminDto adminDto,
                               BindingResult result,
                               Model model,
                              HttpSession session){
        try {
//            session.removeAttribute("message");
            if (result.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                result.toString();
                return "register";
            }

            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            if (admin != null){
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("emailError","Your email has been registered!");
//                session.setAttribute("message","Your email has been registered!");

                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                model.addAttribute("success","Register Successfully!");
//                session.setAttribute("message","Register Successfully!");

                return "register";
            }else {
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("passwordError","Your password maybe wrong! Check again!");
//                session.setAttribute("message","Password is not same!");
                return "register";
            }

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","The server has been wrong!");
//            session.setAttribute("message","Can not register because error server!");
        }

        return "register";
    }
}
