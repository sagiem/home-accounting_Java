package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.exception.ProductAddError;
import ru.sagiem.whattobuy.mapper.ProductMapper;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.CategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.SubcategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.UnitOfMeasurementProductRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final SubcategoryProductRepository subcategoryProductRepository;
    private final UnitOfMeasurementProductRepository unitOfMeasurementProductRepository;
    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final ProductMapper productMapper;


    public List<ProductDtoResponse> showAll(UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        assert user != null;
        List<FamilyGroup> familyGroups = user.getFamilyGroup();
        List<Product> products = productRepository.findByUserCreatorOrFamilyGroupIn(user, familyGroups).orElse(null);

        if (products != null) {
            return products.stream()
                    .map(productMapper::convertToDTO)
                    .toList();

        }

        return null;


    }

    public ResponseEntity<?> addProduct(ProductDtoRequest productDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (productDto.getFamilyGroupId() != null) {

            var product = Product.builder()
                    .name(productDto.getName())
                    .category(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow())
                    .subcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow())
                    .unitOfMeasurement(unitOfMeasurementProductRepository.findById(productDto.getUnitOfMeasurementId()).orElseThrow())
                    .userCreator(userRepository.findByEmail(userDetails.getUsername()).orElseThrow())
                    .familyGroup(familyGroupRepository.getReferenceById(productDto.getFamilyGroupId()))
                    .build();
            var saveProduct = productRepository.save(product);

            return ResponseEntity.ok(saveProduct.getId());

        }
        return new ResponseEntity<>(new ProductAddError(HttpStatus.BAD_REQUEST.value(),
                "Неверные данные"), HttpStatus.BAD_REQUEST);

    }


    public ProductDtoResponse searchId(Integer id, UserDetails userDetails) {

        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        if (user.getFamilyGroup() != null) {
            List<FamilyGroup> familyGroup = user.getFamilyGroup();


            return productMapper.convertToDTO(productRepository.findByIdAndFamilyGroup(id, familyGroup));

        }

        return productMapper.convertToDTO(productRepository.findByIdAndUserCreator(id, user));
    }

    public ProductDtoResponse update(Integer id, ProductDtoRequest productDto, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        List<FamilyGroup> familyGroup = user.getFamilyGroup();
        Product product = productRepository.getReferenceById(id);

        if (product.getUserCreator() == user || familyGroup.contains(product.getFamilyGroup())) {
            product.setId(id);
            product.setCategory(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow());
            product.setSubcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow());
            product.setName(productDto.getName());
            product.setUnitOfMeasurement(unitOfMeasurementProductRepository.findById(productDto.getUnitOfMeasurementId()).orElseThrow());
            productRepository.save(product);

            return productMapper.convertToDTO(productRepository.getReferenceById(id));
        }

        return null;
    }


}
