package org.ptit.service.mapper;

import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.ptit.domain.Authority593;
import org.ptit.domain.User593;
import org.ptit.service.dto.AdminUserDTO;
import org.ptit.service.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link User593} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User593> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).toList();
    }

    public UserDTO userToUserDTO(User593 user) {
        return new UserDTO(user);
    }

    public List<AdminUserDTO> usersToAdminUserDTOs(List<User593> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).toList();
    }

    public AdminUserDTO userToAdminUserDTO(User593 user) {
        return new AdminUserDTO(user);
    }

    public List<User593> userDTOsToUsers(List<AdminUserDTO> userDTOs) {
        return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).toList();
    }

    public User593 userDTOToUser(AdminUserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User593 user = new User593();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority593> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    private Set<Authority593> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority593> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(string -> {
                        Authority593 auth = new Authority593();
                        auth.setName(string);
                        return auth;
                    })
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

    public User593 userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User593 user = new User593();
        user.setId(id);
        return user;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public UserDTO toDtoId(User593 user) {
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<UserDTO> toDtoIdSet(Set<User593> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<UserDTO> userSet = new HashSet<>();
        for (User593 userEntity : users) {
            userSet.add(this.toDtoId(userEntity));
        }

        return userSet;
    }

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public UserDTO toDtoLogin(User593 user) {
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        return userDto;
    }

    @Named("loginSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public Set<UserDTO> toDtoLoginSet(Set<User593> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<UserDTO> userSet = new HashSet<>();
        for (User593 userEntity : users) {
            userSet.add(this.toDtoLogin(userEntity));
        }

        return userSet;
    }
}
