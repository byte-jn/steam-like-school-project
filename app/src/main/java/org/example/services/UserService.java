package org.example.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;

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

    /**
     * Persists a new user from the given DTO and returns the saved DTO.
     */
    public UserDto save(UserDto dto) {
        User user = userMapper.toDomain(dto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Returns a user DTO by id, or empty if not found.
     */
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    /**
     * Returns a user DTO by username, or empty if not found.
     */
    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    /**
     * Returns a user DTO by email, or empty if not found.
     */
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    /**
     * Returns all users as DTOs.
     */
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing user from the given DTO.
     */
    public void update(UserDto dto) {
        User user = userMapper.toDomain(dto);
        userRepository.update(user);
    }

    /**
     * Deletes a user by id.
     */
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
