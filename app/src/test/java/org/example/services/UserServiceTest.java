package org.example.services;

import org.example.dtos.UserDto;
import org.example.entities.User;
import org.example.mappers.UserMapper;
import org.example.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    public void save_persistsAndReturnsMappedDto() {
        UserDto dto = new UserDto();
        dto.setUsername("jannis");

        User user = new User("jannis", "jannis");
        UserDto returned = new UserDto();
        returned.setUsername("jannis");

        when(userMapper.toDomain(dto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(returned);

        UserDto result = userService.save(dto);

        verify(userRepository).save(user);
        assertEquals("jannis", result.getUsername());
    }

    @Test
    public void findByUsername_returnsMappedDto_whenFound() {
        User user = new User("jannis", "jannis");
        UserDto dto = new UserDto();
        dto.setUsername("jannis");

        when(userRepository.findByUsername("jannis")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        Optional<UserDto> result = userService.findByUsername("jannis");

        assertTrue(result.isPresent());
        assertEquals("jannis", result.get().getUsername());
    }

    @Test
    public void findByUsername_returnsEmpty_whenNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.findByUsername("ghost");

        assertFalse(result.isPresent());
    }

    @Test
    public void findByEmail_returnsMappedDto_whenFound() {
        User user = new User("jannis", "jannis");
        UserDto dto = new UserDto();
        dto.setEmail("jannis@example.com");

        when(userRepository.findByEmail("jannis@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        Optional<UserDto> result = userService.findByEmail("jannis@example.com");

        assertTrue(result.isPresent());
        assertEquals("jannis@example.com", result.get().getEmail());
    }

    @Test
    public void findByEmail_returnsEmpty_whenNotFound() {
        when(userRepository.findByEmail("nobody@example.com")).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.findByEmail("nobody@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    public void findAll_returnsAllMappedDtos() {
        User u1 = new User("alice", "alice");
        User u2 = new User("bob", "bob");
        UserDto dto1 = new UserDto();
        dto1.setUsername("alice");
        UserDto dto2 = new UserDto();
        dto2.setUsername("bob");

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));
        when(userMapper.toDto(u1)).thenReturn(dto1);
        when(userMapper.toDto(u2)).thenReturn(dto2);

        List<UserDto> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("alice", result.get(0).getUsername());
        assertEquals("bob", result.get(1).getUsername());
    }

    @Test
    public void update_delegatesToRepository() {
        UserDto dto = new UserDto();
        dto.setUsername("jannis");

        User user = new User("jannis", "jannis");
        when(userMapper.toDomain(dto)).thenReturn(user);

        userService.update(dto);

        verify(userRepository).update(user);
    }

    @Test
    public void delete_delegatesToRepository() {
        userService.delete(42L);

        verify(userRepository).delete(42L);
    }
}
