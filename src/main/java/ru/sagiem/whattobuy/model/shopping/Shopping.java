package ru.sagiem.whattobuy.model.shopping;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_shopping")
public class Shopping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    private LocalDateTime executorDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer volume;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private PointShopping pointShopping;

    @ManyToOne
    @JoinColumn(name = "family_group_id")
    private FamilyGroup familyGroup;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_creator_id", nullable = false, updatable = false)
    private User userCreator;

    @ManyToOne
    @JoinColumn(name = "user_executor_id")
    private User userExecutor;

    @ManyToOne
    @JoinColumn(name = "shopping_project")
    private ShoppingProject shoppingProject;

    @Enumerated(EnumType.STRING)
    private ShoppingStatus shoppingStatus;


}
