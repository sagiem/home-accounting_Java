package ru.sagiem.whattobuy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "families")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "family")
//    private List<User> users;


}
