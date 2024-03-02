package com.library.library.entity;

import com.library.library.model.Address;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    //@NotBlank(message="provide email")
    @Column(name = "full_name")
    private String name;
    private String email;
    private String password;
    @Column(name = "phone_number")
    private  String phoneNumber;
    private String gender;
    @Column(name = "owner")
    private Boolean isOwner = false;
    private String address ;
}
