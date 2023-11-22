package com.libraryplusplus.dto;

import com.libraryplusplus.entity.User;
import com.libraryplusplus.utils.PasswordUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class AuthDTO {
    @NotEmpty(message = "Email mustn't be empty.")
    @Pattern(regexp = "^[^\\s]+$", message = "Email can't contain spaces")
    private String email;
    @NotEmpty(message = "Password mustn't be empty.")
    @Pattern(regexp = "^[^\\s]+$", message = "Password can't contain spaces")
    private String password;

    public User ConvertToUser(){
        User user = new User();
        user.setEmail(this.getEmail());
        user.setPassword(PasswordUtils.hashPassword(this.getPassword()));
        return user;
    }
}
