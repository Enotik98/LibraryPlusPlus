package com.libraryplusplus.service;

import com.libraryplusplus.dto.UserDTO;
import com.libraryplusplus.entity.Role;
import com.libraryplusplus.entity.User;
import com.libraryplusplus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        System.out.println("users: " + users);
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(UserDTO.ConvertToDTO(user));
        }
        return result;
    }
    public User getUserLogin(String email){
        return userRepository.findByEmail(email);
    }

    public boolean addUser(UserDTO userDTO) {
        //add code for search duplicate user
        User exUser = userRepository.findByEmail(userDTO.getEmail());
        if (exUser != null) {
            return false;
        }
        User user = userDTO.ConvertToUser();
        userRepository.save(user);
        return true;
    }

    public boolean updateUserRole(int id, Role role) {
        User user = userRepository.findById(id);
        if (user == null) {
            return false;
        }
        user.setRole(role);
        userRepository.save(user);
        return true;
    }
    public boolean updateRestrictions(int id, boolean sanction, boolean blocked){
        User user = userRepository.findById(id);
        if (user == null){
            return false;
        }
        user.setIsBlocked(blocked);
        user.setIsSanctions(sanction);
        userRepository.save(user);
        return true;
    }

    public boolean updateUser(UserDTO userDTO) {
        User exUser = userRepository.findById(userDTO.getId());
        if (exUser != null) {
            return false;
        }
        User user = userDTO.ConvertToUser();
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(int id) {
        userRepository.deleteById(id);
        return true;
    }


}
