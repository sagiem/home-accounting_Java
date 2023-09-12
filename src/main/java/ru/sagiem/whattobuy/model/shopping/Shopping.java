package ru.sagiem.whattobuy.model.shopping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.Profile;
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
    @JoinColumn(name = "prifile_id")
    private Profile profile;


}
