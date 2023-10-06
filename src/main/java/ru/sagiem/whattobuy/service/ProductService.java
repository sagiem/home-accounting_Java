package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.auth.ProductAddRequest;
import ru.sagiem.whattobuy.exceptions.ProductAddError;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.CategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.SubcategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.UnitOfMeasurementProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final SubcategoryProductRepository subcategoryProductRepository;
    private final UnitOfMeasurementProductRepository unitOfMeasurementProductRepository;
    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;


    public ResponseEntity<?> showAll(UserDetails userDetails) {
        return ResponseEntity.ok(getAllProductByUserOrfamilyGroup(userDetails));
    }

    public ResponseEntity<?> addProduct(ProductAddRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (request.getName() != null &&
                request.getCategoryId() != null &&
                request.getSubcategoryId() != null &&
                request.getUnitOfMeasurementId() != null) {

            var product = Product.builder()
                    .name(request.getName())
                    .category(categoryProductRepository.findById(request.getCategoryId()).orElseThrow())
                    .subcategory(subcategoryProductRepository.findById(request.getSubcategoryId()).orElseThrow())
                    .unitOfMeasurement(unitOfMeasurementProductRepository.findById(request.getUnitOfMeasurementId()).orElseThrow())
                    .user(userRepository.findByEmail(userDetails.getUsername()).orElseThrow())
                    .familyGroup(familyGroupRepository.findByOwnerUserId_Email(userDetails.getUsername()).orElse(null))
                    .build();
            var saveProduct = productRepository.save(product);

            return ResponseEntity.ok(saveProduct.getId());

        }
        return new ResponseEntity<>(new ProductAddError(HttpStatus.BAD_REQUEST.value(),
                "Неверные данные"), HttpStatus.BAD_REQUEST);

    }


    // Возвращает список Product созданный пользователем либо группой куда входит данный пользователь
    private Optional<Product> getAllProductByUserOrfamilyGroup(UserDetails userDetails) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        FamilyGroup familyGroup = user.orElseThrow().getUsersFamilyGroup();
        return productRepository.findAllByNameOrFamilyGroup(userDetails.getUsername(), Optional.ofNullable(familyGroup));

    }
}
