package org.example.mappers;

import org.example.dtos.UserDto;
import org.example.entities.User;

import java.util.ArrayList;

public class UserMapper {

    /**
     * Maps a User domain entity to a UserDto.
     */
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setOwnedGamesIds(new ArrayList<>(user.getOwnedGamesIds()));
        dto.setOwnedDlcIds(new ArrayList<>(user.getOwnedDLCsIds()));
        return dto;
    }

    /**
     * Maps a UserDto to a User domain entity.
     * When the dto carries an id it means we are updating an existing user,
     * so the constructor-set firstname/lastname are overwritten afterwards.
     */
    public User toDomain(UserDto dto) {
        User user = new User(dto.getFirstname(), dto.getLastname());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        if (dto.getOwnedGamesIds() != null) {
            user.setOwnedGamesIds(new ArrayList<>(dto.getOwnedGamesIds()));
        }
        if (dto.getOwnedDlcIds() != null) {
            user.setOwnedDLCsIds(new ArrayList<>(dto.getOwnedDlcIds()));
        }
        return user;
    }
}
