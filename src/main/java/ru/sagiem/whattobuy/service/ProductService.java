package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.ProductDtoRequest;
import ru.sagiem.whattobuy.dto.ProductDtoResponse;
import ru.sagiem.whattobuy.exception.FamilyGroupNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.exception.ProductNotFoundException;
import ru.sagiem.whattobuy.mapper.ProductMapper;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.Product;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
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

    public List<ProductDtoResponse> showAllInGroup(Integer familyGroupId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroup == null) {
            throw new FamilyGroupNotFoundException();
        }
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            List<Product> products = productRepository.findAllByFamilyGroup(familyGroup).orElse(null);
            if (products == null) {
                return null;
            } else {
                return products.stream()
                        .map(productMapper::convertToDTO)
                        .toList();
            }
        } else {
            throw new FamilyGroupNotUserException();
        }
    }

    public List<ProductDtoResponse> showAllInCategory(Integer familyGroupId, Integer categoryId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        CategoryProduct category = categoryProductRepository.findById(categoryId).orElse(null);
        if (familyGroup == null && category == null) {
            throw new FamilyGroupNotFoundException();
        }
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            List<Product> products = productRepository.findAllByFamilyGroupAndCategory(familyGroup, category).orElse(null);
            if (products == null) {
                return null;
            } else {
                return products.stream()
                        .map(productMapper::convertToDTO)
                        .toList();
            }
        } else {
            throw new FamilyGroupNotUserException();
        }
    }

    public List<ProductDtoResponse> showAllInSubcategory(Integer familyGroupId, Integer subcategoryId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        SubcategoryProduct subcategory = subcategoryProductRepository.findById(subcategoryId).orElse(null);
        if (familyGroup == null && subcategory == null) {
            throw new FamilyGroupNotFoundException();
        }
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            List<Product> products = productRepository.findAllByFamilyGroupAndSubcategory(familyGroup, subcategory).orElse(null);
            if (products == null) {
                return null;
            } else {
                return products.stream()
                        .map(productMapper::convertToDTO)
                        .toList();
            }
        } else {
            throw new FamilyGroupNotUserException();
        }
    }

    public ResponseEntity<?> add(ProductDtoRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FamilyGroup familyGroup = familyGroupRepository.findById(request.getFamilyGroupId()).orElse(null);
        User user = familyGroupAndUserUtils.getUser(userDetails);

        if (familyGroup == null) {
            throw new FamilyGroupNotFoundException();

        }

        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroup)) {
            var product = Product.builder()
                    .name(request.getName())
                    .userCreator(user)
                    .category(categoryProductRepository.findById(request.getCategoryId()).orElse(null))
                    .subcategory(subcategoryProductRepository.findById(request.getSubcategoryId()).orElse(null))
                    .unitOfMeasurement(UnitOfMeasurementProduct.valueOf(request.getUnitOfMeasurement()))
                    .familyGroup(familyGroup)
                    .build();
            var saveProduct = productRepository.save(product);
            return ResponseEntity.ok(saveProduct.getId());
        } else {
            throw new FamilyGroupNotUserException();
        }

    }

    public ProductDtoResponse searchId(Integer id) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, product.getFamilyGroup())) {
            return productMapper.convertToDTO(product);
        } else {
            throw new FamilyGroupNotUserException();
        }

    }

    public String update(Integer id, ProductDtoRequest productDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, product.getFamilyGroup().getId())
                || (product.getUserCreator().equals(familyGroupAndUserUtils.getUser(userDetails)))) {
            product.setName(productDto.getName());
            product.setCategory(categoryProductRepository.findById(productDto.getCategoryId()).orElse(null));
            product.setSubcategory(subcategoryProductRepository.findById(productDto.getSubcategoryId()).orElse(null));
            product.setUnitOfMeasurement(UnitOfMeasurementProduct.valueOf(productDto.getUnitOfMeasurement()));
            productRepository.save(product);
            return productDto.getName();
        } else {
            throw new FamilyGroupNotUserException();
        }
    }

    public String delete(Integer id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        User user = familyGroupAndUserUtils.getUser(userDetails);
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, product.getFamilyGroup().getId())
                || (product.getUserCreator().equals(user))) {
            productRepository.deleteById(id);
            return product.getName();
        } else {
            throw new FamilyGroupNotUserException();
        }
    }


}
