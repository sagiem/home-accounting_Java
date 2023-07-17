package ru.sagiem.whattobuy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    @Column(name = "email")
    @Email
    private String email;

//    @ManyToOne
//    @JoinColumn(name = "family_id")
//    private Family family;
}
