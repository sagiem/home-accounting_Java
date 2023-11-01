package ru.sagiem.whattobuy.model.shopping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.Role;
import ru.sagiem.whattobuy.model.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_shopping")
public class Shopping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataShoping;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Double price;
    private Integer Volume;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private PointShopping pointShopping;

    private boolean toWork;
    private boolean executed;

    @ManyToOne
    @JoinColumn(name = "family_group_id")
    private FamilyGroup familyGroup;

    @ManyToOne
    @JoinColumn(name = "user_creator_id")
    private User userCreator;

    @ManyToOne
    @JoinColumn(name = "user_executor_id")
    private User userExecutor;

    @Enumerated(EnumType.STRING)
    private ShoppingStatus shoppingStatus;


}
