package com.libraryplusplus.dto;

import com.libraryplusplus.entity.User;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.utils.PasswordUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address; // type for text ?
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
        dto.setPassword(user.getPassword());
        dto.setIsSanctions(user.getIsSanctions());
        dto.setIsBlocked(user.getIsBlocked());
        return dto;
    }
    public User ConvertToUser( ){
        User user = new User();
        user.setId(this.getId());
        user.setFirst_name(this.getFirst_name());
        user.setLast_name(this.getLast_name());
        user.setEmail(this.getEmail());
        user.setPhone(this.getPhone());
        user.setAddress(this.getAddress());
//        user.setRole(this.getRole());
        user.setPassword(PasswordUtils.hashPassword(this.getPassword()));
        user.setIsSanctions(this.isSanctions);
        user.setIsBlocked(this.isBlocked);
        return user;
    }
    //

}
