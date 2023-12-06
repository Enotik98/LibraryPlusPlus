package com.libraryplusplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.utils.PasswordUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthDTO {
    @NotEmpty(message = "Email mustn't be empty.")
    @Pattern(regexp = "^[^\\s]+$", message = "Email can't contain spaces.")
    @Email(message = "Email format should be valid.")
    @Size(min = 6, message = "The email length must be more than 6 characters.")
    @Size(max = 25, message = "The email length must be less than 25 characters.")
    private String email;
    @NotEmpty(message = "Password mustn't be empty.")
    @Pattern(regexp = "^[^\\s]+$", message = "Password can't contain spaces.")
    @Size(min = 3, message = "The password length must be more than 3 characters.")
    @Size(max = 15, message = "The password length must be less than 15 characters.")
    private String password;

    public User ConvertToUser(){
        User user = new User();
        user.setEmail(this.getEmail());
        user.setPassword(PasswordUtils.hashPassword(this.getPassword()));
        return user;
    }
}
