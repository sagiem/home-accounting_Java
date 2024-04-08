package ru.sagiem.whattobuy.model.shopping;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.sagiem.whattobuy.model.token.Token;
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
@Table(name = "_shopping_project")
public class ShoppingProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    private LocalDateTime finishDate;
    private boolean active;
    private String comment;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_creator_id", nullable = false, updatable = false)
    private User userCreator;

    @ManyToOne
    @JoinColumn(name = "family_group", nullable = false)
    private FamilyGroup familyGroup;

    @OneToMany(mappedBy = "shoppingProject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shopping> shoppings;
}
