package com.deedee.profile.mapper;

import com.deedee.profile.dto.ProfileDto;
import com.deedee.profile.entity.Profile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    ProfileDto toProfileDto (Profile profile);

    @Mapping(target = "id", ignore = true)
    Profile toProfile (ProfileDto profileDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile (@MappingTarget Profile profile, ProfileDto profileDto);
}
