package com.libraryplusplus.dto;

import com.libraryplusplus.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class AuthDTO {
    @NotEmpty(message = "Email mustn't be empty")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^[^\\s]+$", message = "Password can't contain spaces")
    private String password;

    public User ConvertToUser(){
        User user = new User();
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        return user;
    }
}
