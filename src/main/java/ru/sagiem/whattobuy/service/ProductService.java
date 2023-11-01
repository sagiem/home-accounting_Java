package ru.sagiem.whattobuy.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.auth.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.auth.ProductDtoResponse;
import ru.sagiem.whattobuy.exceptions.ProductAddError;
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

import java.util.List;

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
        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        if (user.getUsersFamilyGroup() != null) {
            FamilyGroup familyGroup = user.getUsersFamilyGroup();

            List<Product> products = productRepository.findAllByFamilyGroup(familyGroup).orElse(null);
            if(products != null) {
                return products.stream()
                        .map(productMapper::convertToDTO)
                        .toList();
            }

            return null;

        }

        List<Product> products = productRepository.findAllByUserCreator(user).orElse(null);
        if(products != null) {
            return products.stream()
                    .map(productMapper::convertToDTO)
                    .toList();
        }

        return null;

    }

    public ResponseEntity<?> addProduct(ProductDtoRequest productDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (productDto.getName() != null &&
                productDto.getCategoryId() != null &&
                productDto.getSubcategoryId() != null &&
                productDto.getUnitOfMeasurementId() != null) {

            var product = Product.builder()
                    .name(productDto.getName())
                    .category(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow())
                    .subcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow())
                    .unitOfMeasurement(unitOfMeasurementProductRepository.findById(productDto.getUnitOfMeasurementId()).orElseThrow())
                    .userCreator(userRepository.findByEmail(userDetails.getUsername()).orElseThrow())
                    .familyGroup(familyGroupRepository.findByOwnerUserId_Email(userDetails.getUsername()).orElse(null))
                    .build();
            var saveProduct = productRepository.save(product);

            return ResponseEntity.ok(saveProduct.getId());

        }
        return new ResponseEntity<>(new ProductAddError(HttpStatus.BAD_REQUEST.value(),
                "Неверные данные"), HttpStatus.BAD_REQUEST);

    }



    public ProductDtoResponse searchId(Integer id, UserDetails userDetails) {

        var user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        System.out.println(id);

        if (user.getUsersFamilyGroup() != null) {
            FamilyGroup familyGroup = user.getUsersFamilyGroup();

            return productMapper.convertToDTO(productRepository.findByIdAndFamilyGroup(id, familyGroup));

        }

        return productMapper.convertToDTO(productRepository.findByIdAndUserCreator(id, user));
    }

    public ProductDtoResponse update(Integer id, ProductDtoRequest productDto, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FamilyGroup familyGroup = user.getUsersFamilyGroup();
        Product product = productRepository.getReferenceById(id);

        if (product.getUserCreator() == user || product.getFamilyGroup() == familyGroup) {
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
