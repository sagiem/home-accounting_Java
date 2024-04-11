package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.CategoryProductDtoRequest;
import ru.sagiem.whattobuy.dto.CategoryProductDtoResponse;
import ru.sagiem.whattobuy.exception.CategoryProductNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotCreatorException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.mapper.CategoryProductMapper;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.repository.FamilyGroupRepository;
import ru.sagiem.whattobuy.repository.poroduct.CategoryProductRepository;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryProductService {
    private final CategoryProductRepository categoryProductRepository;
    private final FamilyGroupAndUserUtils familyGroupAndUserUtils;
    private final CategoryProductMapper categoryProductMapper;
    private final FamilyGroupRepository familyGroupRepository;

    public List<CategoryProductDtoResponse> showAllForGroup(Integer familyGroupId, UserDetails userDetails) {
        FamilyGroup familyGroup = familyGroupRepository.findById(familyGroupId).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotUserException();
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, familyGroupId)) {
            List<CategoryProduct> categoryProducts = categoryProductRepository.findAllByFamilyGroup(familyGroup).orElse(null);
            if (categoryProducts == null)
                return null;
            else {
                return categoryProducts.stream()
                        .map(categoryProductMapper::convertToDto)
                        .toList();
            }
        }
        throw new FamilyGroupNotUserException();
    }


    public Integer create(CategoryProductDtoRequest request, UserDetails userDetails) {
        FamilyGroup familyGroup = familyGroupRepository.findById(request.getFamilyGroupId()).orElse(null);
        if (familyGroup == null)
            throw new FamilyGroupNotUserException();

        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, request.getFamilyGroupId())) {
            CategoryProduct categoryProduct = CategoryProduct.builder()
                    .name(request.getName())
                    .familyGroup(familyGroup)
                    .build();
            categoryProductRepository.save(categoryProduct);
            return categoryProduct.getId();
        } else
            throw new FamilyGroupNotCreatorException();
    }

    public CategoryProductDtoResponse searchId(Integer id, UserDetails userDetails) {
        CategoryProduct categoryProduct = categoryProductRepository.findById(id).orElse(null);
        if (categoryProduct == null)
            return null;
        if (familyGroupAndUserUtils.isUserInFamilyGroup(userDetails, categoryProduct.getFamilyGroup().getId()))
            return categoryProductMapper.convertToDto(categoryProduct);
        else
            throw new FamilyGroupNotUserException();
    }

    public String update(CategoryProductDtoRequest request, UserDetails userDetails) {
        CategoryProduct categoryProduct = categoryProductRepository.findById(request.getFamilyGroupId()).orElse(null);
        if (categoryProduct == null)
            throw new CategoryProductNotFoundException();
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, categoryProduct.getFamilyGroup().getId())) {
            categoryProduct.setName(request.getName());
            categoryProductRepository.save(categoryProduct);
            return request.getName();
        } else
            throw new FamilyGroupNotCreatorException();
    }

    public String delete(Integer id, UserDetails userDetails) {
        CategoryProduct categoryProduct = categoryProductRepository.findById(id).orElse(null);
        if (categoryProduct == null)
            throw new CategoryProductNotFoundException();
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, categoryProduct.getFamilyGroup().getId())) {
            categoryProductRepository.delete(categoryProduct);
            return categoryProduct.getName();
        } else
            throw new FamilyGroupNotCreatorException();
    }
}
