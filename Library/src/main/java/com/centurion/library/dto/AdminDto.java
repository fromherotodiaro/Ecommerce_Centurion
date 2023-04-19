package com.centurion.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @NotNull(message = "First Name cannot be null")
    @Size(min = 2, max = 10, message = "Invalid first name!(2-10 Characters)")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Size(min = 2, max = 10, message = "Invalid first name!(2-10 Characters)")
    private String lastName;
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 5, max = 15, message = "Invalid first name!(5-15 Characters)")
    private String password;
    private String repeatPassword;
}
