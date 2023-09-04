package ru.sagiem.whattobuy.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private CategoryProduct category;  // многие к одному

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SubcategoryProduct subcategory;  // многие к одному

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UnitOfMeasurement unitOfMeasurement; // многие к одному
}
