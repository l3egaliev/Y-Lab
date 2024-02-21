package kg.rakhim.classes.utils;

import kg.rakhim.classes.dto.AuthorizeDTO;
import kg.rakhim.classes.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapToUser {
    @Mapping(source = "username", target="username")
    @Mapping(source = "password", target="password")
    User dtoToUser(AuthorizeDTO dto);
}
