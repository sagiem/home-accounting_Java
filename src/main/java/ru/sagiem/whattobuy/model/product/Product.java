package ru.sagiem.whattobuy.model.product;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.sagiem.whattobuy.model.shopping.Shopping;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryProduct category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubcategoryProduct subcategory;

    @Enumerated
    private UnitOfMeasurementProduct unitOfMeasurement;

    @OneToMany(mappedBy = "product")
    private List<Shopping> shoppings;

    @ManyToOne
    @JoinColumn(name = "family_group_id", nullable = false)
    private FamilyGroup familyGroup;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_creator_id", nullable = false, updatable = false)
    private User userCreator;


}