package com.deedee.profile.mapper;

import com.deedee.profile.dto.ProfileChangeLogDto;
import com.deedee.profile.dto.ProfileDto;
import com.deedee.profile.entity.ProfileChangeLog;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileChangeLogMapper {

    ProfileChangeLogMapper INSTANCE = Mappers.getMapper(ProfileChangeLogMapper.class);

    ProfileChangeLog toProfileChangeLog (ProfileChangeLogDto profileChangeLogDto);

    ProfileChangeLogDto toProfileChangeLogDto (ProfileChangeLog profileChangeLog);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProfileChangeLog profileToProfileChangLog (ProfileDto profileDto);
}
