package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Inject
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto save(UserDto dto) {
        User user = userMapper.toDomain(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::toDto);
    }

    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> update(Long id, UserDto dto) {
        Optional<User> found = userRepository.findById(id);
        if (found.isEmpty()) {
            return Optional.empty();
        }
        User existing = found.get();
        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setFirstname(dto.getFirstname());
        existing.setLastname(dto.getLastname());
        if (dto.getOwnedGamesIds() != null) {
            existing.setOwnedGamesIds(new ArrayList<>(dto.getOwnedGamesIds()));
        }
        if (dto.getOwnedDlcIds() != null) {
            existing.setOwnedDLCsIds(new ArrayList<>(dto.getOwnedDlcIds()));
        }
        return Optional.of(userMapper.toDto(userRepository.update(existing)));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
