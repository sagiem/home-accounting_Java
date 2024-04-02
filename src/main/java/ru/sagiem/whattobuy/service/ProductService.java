package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.exception.FamilyGroupNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.exception.ProductNotFoundException;
import ru.sagiem.whattobuy.mapper.ProductMapper;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.product.UnitOfMeasurementProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.User;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.UserRepository;
import ru.sagiem.whattobuy.repository.poroduct.CategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.ProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.SubcategoryProductRepository;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

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
    private final FamilyGroupAndUserUtils familyGroupAndUserUtils;


    public List<ProductDtoResponse> showAll(UserDetails userDetails, Integer familyGroupId) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroup != null)
            throw new FamilyGroupNotFoundException();
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            List<Product> products = productRepository.findAllByFamilyGroup(familyGroup).orElse(null);
            if (products == null)
                return null;
            else {
                return products.stream()
                        .map(productMapper::convertToDTO)
                        .toList();
            }
        } else
            throw new FamilyGroupNotUserException();

    }

    public ResponseEntity<?> add(Integer familyGroupId,
                                 ProductDtoRequest productDto,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroup != null)
            throw new FamilyGroupNotFoundException();

        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            var product = Product.builder()
                    .name(productDto.getName())
                    .category(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow())
                    .subcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow())
                    .unitOfMeasurement(UnitOfMeasurementProduct.valueOf(productDto.getUnitOfMeasurement()))
                    .familyGroup(familyGroup)
                    .build();
            var saveProduct = productRepository.save(product);
            return ResponseEntity.ok(saveProduct.getId());
        } else
            throw new FamilyGroupNotUserException();

    }

    public ProductDtoResponse searchId(Integer id, UserDetails userDetails) {

        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            return null;

        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, product.getFamilyGroup()))
            return productMapper.convertToDTO(product);

        else
            throw new FamilyGroupNotUserException();

    }

    public String update(Integer id, ProductDtoRequest productDto, UserDetails userDetails) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            throw new ProductNotFoundException();

        User user = familyGroupAndUserUtils.getUser(userDetails);
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, product.getFamilyGroup().getId())
                || (product.getUserCreator().equals(user)) && familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, product.getFamilyGroup())) {
            product.setName(productDto.getName());
            product.setCategory(categoryProductRepository.findById(productDto.getCategoryId()).orElseThrow());
            product.setSubcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElseThrow());
            product.setUnitOfMeasurement(UnitOfMeasurementProduct.valueOf(productDto.getUnitOfMeasurement()));
            productRepository.save(product);
            return productDto.getName();
        }
        else
            throw new FamilyGroupNotUserException();

    }


    public String delete(Integer id, UserDetails userDetails) {

        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            throw new ProductNotFoundException();

        User user = familyGroupAndUserUtils.getUser(userDetails);
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, product.getFamilyGroup().getId())
                || (product.getUserCreator().equals(user)) && familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, product.getFamilyGroup())) {
            productRepository.deleteById(id);
            return product.getName();
        }
        else
            throw new FamilyGroupNotUserException();
    }
}
