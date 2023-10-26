package ru.sagiem.whattobuy.model.product;

import jakarta.persistence.*;
import lombok.*;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryProduct category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubcategoryProduct subcategory;

    private String name;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitOfMeasurementProduct unitOfMeasurement;

    @OneToMany(mappedBy = "product")
    private List<Shopping> shoppings;

    @ManyToOne
    @JoinColumn(name = "family_group_id")
    private FamilyGroup familyGroup;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private User userCreator;


}