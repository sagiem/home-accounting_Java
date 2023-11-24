package ru.sagiem.whattobuy.mapper;

import org.mapstruct.*;
import ru.sagiem.whattobuy.dto.ShoppingProjectDtoResponse;
import ru.sagiem.whattobuy.model.shopping.ShoppingProject;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShoppingProjectMapper {

    @Mappings({
            @Mapping(source = "createDate", target = "createDate", dateFormat = "dd-MM-yyyy HH:mm:ss",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "lastModified", target = "lastModified", dateFormat = "dd-MM-yyyy HH:mm:ss",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "finishDate", target = "finishDate", dateFormat = "dd-MM-yyyy HH:mm:ss",
                    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "userCreator.id", target = "userCreatorId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(source = "familyGroup.id", target = "familyGroupId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
    })
    ShoppingProjectDtoResponse convertToDto(ShoppingProject shoppingProject);
}
