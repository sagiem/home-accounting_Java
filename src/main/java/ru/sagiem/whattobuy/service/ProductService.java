package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.exception.FamilyGroupNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
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
import ru.sagiem.whattobuy.utils.FamalyGroupAndUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final SubcategoryProductRepository subcategoryProductRepository;
    private final UserRepository userRepository;
    private final FamilyGroupRepository familyGroupRepository;
    private final ProductMapper productMapper;
    private final FamalyGroupAndUserUtils famalyGroupAndUserUtils;


    public List<ProductDtoResponse> showAll(UserDetails userDetails, Integer familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroup!= null)
            throw new FamilyGroupNotFoundException();
        if (famalyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            List<Product> products = productRepository.findAllByFamilyGroup(familyGroup).orElse(null);
            if (products == null)
                return null;
            else {
                return products.stream()
                        .map(productMapper::convertToDTO)
                        .toList();
            }
        }
        else
            throw new FamilyGroupNotUserException();

    }

    public ResponseEntity<?> addProduct(ProductDtoRequest productDto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        if (productDto.getFamilyGroupId() != null) {

            var product = Product.builder()
                    .name(productDto.getName())
                    .category(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow())
                    .subcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow())
                    .unitOfMeasurement(unitOfMeasurementProductRepository.findById(productDto.getUnitOfMeasurementId()).orElseThrow())
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

        if (user.getFamilyGroups() != null) {
            List<FamilyGroup> familyGroup = user.getFamilyGroups();


            return productMapper.convertToDTO(productRepository.findByIdAndFamilyGroupIn(id, familyGroup));

        }

        return productMapper.convertToDTO(productRepository.findByIdAndUserCreator(id, user));
    }

    public ProductDtoResponse update(Integer id, ProductDtoRequest productDto, UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        List<FamilyGroup> familyGroup = user.getFamilyGroups();
        Product product = productRepository.getReferenceById(id);

        if (product.getUserCreator() == user || familyGroup.contains(product.getFamilyGroup())) {
            product.setId(id);
            product.setCategory(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow());
            product.setSubcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow());
            product.setName(productDto.getName());
            product.setUnitOfMeasurement(unitOfMeasurementProductRepository.findById(productDto.getUnitOfMeasurementId()).orElseThrow());
            product.setFamilyGroup(familyGroupRepository.getReferenceById(productDto.getFamilyGroupId()));
            productRepository.save(product);

            return productMapper.convertToDTO(productRepository.getReferenceById(id));
        }

        return null;
    }


}
