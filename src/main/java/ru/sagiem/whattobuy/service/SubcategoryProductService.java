package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.SubCategoryProductDtoRequest;
import ru.sagiem.whattobuy.dto.SubCategoryProductDtoResponse;
import ru.sagiem.whattobuy.exception.CategoryProductNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotCreatorException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotUserException;
import ru.sagiem.whattobuy.exception.SubCategoryProductNotFoundException;
import ru.sagiem.whattobuy.mapper.SubCategoryProductMapper;
import ru.sagiem.whattobuy.model.product.CategoryProduct;
import ru.sagiem.whattobuy.model.product.SubcategoryProduct;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.repository.poroduct.CategoryProductRepository;
import ru.sagiem.whattobuy.repository.poroduct.SubcategoryProductRepository;
import ru.sagiem.whattobuy.utils.FamilyGroupAndUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryProductService {
    private final FamilyGroupAndUserUtils familyGroupAndUserUtils;
   private final SubcategoryProductRepository subcategoryProductRepository;
   private final CategoryProductRepository categoryProductRepository;
   private final SubCategoryProductMapper subCategoryProductMapper;


    public List<SubCategoryProductDtoResponse> showAll(Integer CategoryProductId, UserDetails userDetails) {
        List<SubcategoryProduct> subcategoryProducts = subcategoryProductRepository.findAllByCategoryProductId(CategoryProductId);
        if(subcategoryProducts == null)
            return null;
        CategoryProduct categoryProduct = categoryProductRepository.findById(CategoryProductId).orElse(null);
        FamilyGroup categoryProductFamilyGroup = categoryProduct.getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);
        if (userFamilyGroup.equals(categoryProductFamilyGroup)){
            return subcategoryProducts.stream()
                    .map(subCategoryProductMapper::convertToDto)
                    .toList();
        }
        throw new FamilyGroupNotUserException();

    }

    public Integer create(SubCategoryProductDtoRequest request, UserDetails userDetails) {
        CategoryProduct categoryProduct = categoryProductRepository.findById(request.getCategoryProductId()).orElse(null);
        if (categoryProduct == null)
            throw new CategoryProductNotFoundException();
        FamilyGroup categoryProductFamilyGroup = categoryProduct.getFamilyGroup();
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, categoryProductFamilyGroup.getId())){
            SubcategoryProduct subcategoryProduct = SubcategoryProduct.builder()
                    .name(request.getName())
                    .familyGroup(categoryProductFamilyGroup)
                    .categoryProduct(categoryProduct)
                    .build();
            return subcategoryProductRepository.save(subcategoryProduct).getId();
        }
        else
            throw new FamilyGroupNotCreatorException();
    }

    public SubCategoryProductDtoResponse searchId(Integer id, UserDetails userDetails) {
        SubcategoryProduct subcategoryProduct = subcategoryProductRepository.findById(id).orElse(null);
        if (subcategoryProduct == null)
            throw new SubCategoryProductNotFoundException();
        FamilyGroup categoryProductFamilyGroup = subcategoryProduct.getCategoryProduct().getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);
        if (userFamilyGroup.equals(categoryProductFamilyGroup)){
            return subCategoryProductMapper.convertToDto(subcategoryProduct);
        }
        throw new FamilyGroupNotUserException();
    }

    public String update(Integer id, String name, UserDetails userDetails) {
        SubcategoryProduct subcategoryProduct = subcategoryProductRepository.findById(id).orElse(null);
        if (subcategoryProduct == null)
            throw new SubCategoryProductNotFoundException();

        FamilyGroup categoryProductFamilyGroup = subcategoryProduct.getCategoryProduct().getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);

        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, categoryProductFamilyGroup.getId())){
            subcategoryProduct.setName(name);
            subcategoryProductRepository.save(subcategoryProduct);
            return name;
        }
        else
            throw new FamilyGroupNotCreatorException();
    }

    public String delete(Integer id, UserDetails userDetails) {
        SubcategoryProduct subcategoryProduct = subcategoryProductRepository.findById(id).orElse(null);
        if (subcategoryProduct == null)
            throw new SubCategoryProductNotFoundException();
        FamilyGroup categoryProductFamilyGroup = subcategoryProduct.getCategoryProduct().getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);
        if (familyGroupAndUserUtils.isUserCreatedInFamilyGroup(userDetails, categoryProductFamilyGroup.getId())){
            subcategoryProductRepository.delete(subcategoryProduct);
            return subcategoryProduct.getName();
        }
        else
            throw new FamilyGroupNotCreatorException();
    }
}
