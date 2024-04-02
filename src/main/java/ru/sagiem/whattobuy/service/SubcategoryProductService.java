package ru.sagiem.whattobuy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sagiem.whattobuy.dto.SubCategoryProductDtoRequest;
import ru.sagiem.whattobuy.dto.SubCategoryProductDtoResponse;
import ru.sagiem.whattobuy.exception.CategoryProductNotFoundException;
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

    public Integer create(Integer categoryProductId, SubCategoryProductDtoRequest request, UserDetails userDetails) {
        CategoryProduct categoryProduct = categoryProductRepository.findById(categoryProductId).orElse(null);
        if (categoryProduct == null)
            throw new CategoryProductNotFoundException();
        FamilyGroup categoryProductFamilyGroup = categoryProduct.getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);
        if (userFamilyGroup.equals(categoryProductFamilyGroup)){
            SubcategoryProduct subcategoryProduct = SubcategoryProduct.builder()
                    .name(request.getName())
                    .familyGroup(categoryProductFamilyGroup)
                    .categoryProduct(categoryProduct)
                    .build();
            return subcategoryProductRepository.save(subcategoryProduct).getId();
        }
        throw new FamilyGroupNotUserException();
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

    public String update(Integer id, SubCategoryProductDtoRequest request, UserDetails userDetails) {
        SubcategoryProduct subcategoryProduct = subcategoryProductRepository.findById(id).orElse(null);
        if (subcategoryProduct == null)
            throw new SubCategoryProductNotFoundException();

        FamilyGroup categoryProductFamilyGroup = subcategoryProduct.getCategoryProduct().getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);

        if (userFamilyGroup.equals(categoryProductFamilyGroup)){
            subcategoryProduct.setName(request.getName());
            subcategoryProductRepository.save(subcategoryProduct);
            return request.getName();
        }
        throw new FamilyGroupNotUserException();
    }

    public String delete(Integer id, UserDetails userDetails) {
        SubcategoryProduct subcategoryProduct = subcategoryProductRepository.findById(id).orElse(null);
        if (subcategoryProduct == null)
            throw new SubCategoryProductNotFoundException();
        FamilyGroup categoryProductFamilyGroup = subcategoryProduct.getCategoryProduct().getFamilyGroup();
        List<FamilyGroup> userFamilyGroup = familyGroupAndUserUtils.getFamilyGroup(userDetails);
        if (userFamilyGroup.equals(categoryProductFamilyGroup)){
            subcategoryProductRepository.delete(subcategoryProduct);
            return subcategoryProduct.getName();
        }
        throw new FamilyGroupNotUserException();
    }
}
