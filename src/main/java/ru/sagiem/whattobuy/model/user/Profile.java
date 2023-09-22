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
@Table(name = "_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "profile")
    private List<Shopping> shoppings;

    @OneToMany(mappedBy = "profile")
    private List<Product> products;
}
