package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sagiem.whattobuy.dto.FamilyGroupDtoResponse;
import ru.sagiem.whattobuy.model.user.FamilyGroup;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = FamilyGroup.class)
public interface FamilyGroupMapper {

    @Mapping(source = "creatorUser.id", target = "creatorUserId")
    @Mapping(source = "creatorUser.username", target = "ownerUsername")
    FamilyGroupDtoResponse convertToDto(FamilyGroup familyGroup);
}
