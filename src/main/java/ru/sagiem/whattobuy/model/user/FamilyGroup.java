package ru.sagiem.whattobuy.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.Shopping;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_family_group")
public class FamilyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;


    @OneToOne
    private User ownerUserId;

    @OneToMany(mappedBy = "usersFamilyGroup")
    private List<User> users;

    //participants

    @OneToMany(mappedBy = "familyGroup")
    private List<Shopping> shoppings;

    @OneToMany(mappedBy = "familyGroup")
    private List<Product> products;
}
