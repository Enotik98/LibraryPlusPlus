package com.libraryplusplus.service;

import com.libraryplusplus.dto.AuthDTO;
import com.libraryplusplus.dto.UserDTO;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.repository.UserRepository;
import com.libraryplusplus.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUser() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> result = new ArrayList<>();
            for (User user : users) {
                result.add(UserDTO.ConvertToDTO(user));
            }
            return result;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    public UserDTO getUserById(int id){
        try{
            User user = userRepository.findById(id);
            return UserDTO.ConvertToDTO(user);
        }catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public User getByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void addUser(AuthDTO authDTO) {
        //add code for search duplicate user
        try {
            User exUser = userRepository.findByEmail(authDTO.getEmail());
            if (exUser != null) {
                //409
                throw new CustomException(HttpStatus.CONFLICT, "An account with such email already exists. Choose another email.");
            }
            User user = authDTO.ConvertToUser();
            userRepository.save(user);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateUserRole(int id, Role role) {
        try {
            User user = userRepository.findById(id);
            if (user == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Account not found.");
            }
            user.setRole(role);
            userRepository.save(user);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateRestrictions(int userId, boolean sanction, boolean blocked, String tokenUserRole) {
        try {
            User user = userRepository.findById(userId);
            if (user == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Account not found");
            }
            if (tokenUserRole.equals("ROLE_LIBRARIAN") && user.getRole().equals(Role.ADMIN)){
                throw new CustomException(HttpStatus.FORBIDDEN, "No access to edit this account.");
            }

            user.setIsBlocked(blocked);
            user.setIsSanctions(sanction);
            userRepository.save(user);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void updateUser(UserDTO userDTO) {
        try {
            User exUser = userRepository.findById(userDTO.getId());
            if (exUser == null) {
                throw new CustomException(HttpStatus.NOT_FOUND, "Account not found");
            }
            User user = userDTO.ConvertToUser(exUser);
            System.out.println(user);
            userRepository.save(user);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void deleteUser(int id) {
        try {
            //add check order with status
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public boolean isUserHaveTicket(User user) {
        return user.getFirst_name() == null || user.getLast_name() == null ||
                user.getPhone() == null || user.getAddress() == null;
    }


}
