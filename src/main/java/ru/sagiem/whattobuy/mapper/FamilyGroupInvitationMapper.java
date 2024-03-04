package ru.sagiem.whattobuy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sagiem.whattobuy.dto.FamilyGroupInvitationDtoRequest;
import ru.sagiem.whattobuy.model.user.FamilyGroup;
import ru.sagiem.whattobuy.model.user.FamilyGroupInvitations;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = FamilyGroupInvitations.class)
public interface FamilyGroupInvitationMapper {

    @Mapping(source = "familyGroup.name", target = "familyGroupName")
    @Mapping(source = "familyGroup.userCreator.username", target = "userSender")
    FamilyGroupInvitationDtoRequest convertToDto(FamilyGroupInvitations invitations);
}
