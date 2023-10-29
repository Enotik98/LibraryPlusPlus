package com.libraryplusplus.entity;


import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

//@org.hibernate.annotations.TypeDef(name)
@org.hibernate.annotations.TypeDef(name = "user_role", typeClass = SQLEnumType.class)
@Table(name = "users", schema = "public")
//@org.hibernate.annotations.TypeD
@Data
@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
    @Type(type = "user_role")
    @Column(name = "role")
    private Role role;
    @Column(name = "password")
    private String password;
    @Column(name = "sanctions")
    private Boolean isSanctions;
    @Column(name = "blocked")
    private Boolean isBlocked;

    @PrePersist
    public void setDefaultValues(){
        if (role == null){
            role = Role.USER;
        }
        if (isSanctions == null){
            isSanctions = false;
        }
        if (isBlocked == null){
            isBlocked = false;
        }
    }

}
