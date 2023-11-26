package com.libraryplusplus.dto;

import com.libraryplusplus.entity.User;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.utils.PasswordUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserDTO {
    private int id;
    @NotEmpty (message = "First name is empty.")
    private String first_name;
    @NotEmpty (message = "Last name is empty.")
    private String last_name;
    @NotEmpty(message = "Email is empty.")
    private String email;

    @NotEmpty (message = "Phone name is empty.")
    private String phone;
    @NotEmpty (message = "Address name is empty.")
    private String address;
    private Role role;
    private String password;
    private Boolean isSanctions;
    private Boolean isBlocked;

    public static UserDTO ConvertToDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirst_name(user.getFirst_name());
        dto.setLast_name(user.getLast_name());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());
//        dto.setPassword(user.getPassword());
        dto.setIsSanctions(user.getIsSanctions());
        dto.setIsBlocked(user.getIsBlocked());
        return dto;
    }
    public User ConvertToUser(User user){
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setFirst_name(this.getFirst_name());
        newUser.setLast_name(this.getLast_name());
        newUser.setEmail(this.getEmail());
        newUser.setPhone(this.getPhone());
        newUser.setAddress(this.getAddress());
        newUser.setRole(user.getRole());
        newUser.setIsSanctions(user.getIsSanctions());
        newUser.setIsBlocked(user.getIsBlocked());
        newUser.setPassword(user.getPassword());
        return newUser;
    }
    //

}
