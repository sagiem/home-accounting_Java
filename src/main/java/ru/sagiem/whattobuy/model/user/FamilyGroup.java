package ru.sagiem.whattobuy.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.shopping.PointShopping;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "family_group")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class FamilyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private User userCreator;

    @ManyToMany
    private List<User> users;

//    @OneToMany(mappedBy = "familyGroup")
//    private List<Shopping> shoppings;

    @OneToMany(mappedBy = "familyGroup")
    private List<Product> products;

    @OneToMany(mappedBy = "familyGroup")
    private List<PointShopping> pointShoppings;

    @OneToMany(mappedBy = "familyGroup")
    private List<ShoppingProject> shoppingProjects;
}
