package com.digitalcomanetwork.kosterme.userservice.mapper;

import com.digitalcomanetwork.kosterme.userservice.data.UserEntity;
import com.digitalcomanetwork.kosterme.userservice.shared.UserDto;
import com.digitalcomanetwork.kosterme.userservice.ui.model.CreateUserRequestModel;
import com.digitalcomanetwork.kosterme.userservice.ui.model.CreateUserResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserEntity userDtoToUserEntity(UserDto userDetails);
    UserDto userRequestToUserDto(CreateUserRequestModel userDetails);
    CreateUserResponseModel userDtoToUserResponse(UserDto userDetails);
    UserDto userEntityToUserDto(UserEntity userEntity);
}
