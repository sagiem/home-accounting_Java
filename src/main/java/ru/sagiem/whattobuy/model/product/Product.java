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
}
