package org.example.services;

import org.example.dtos.GameDto;
import org.example.entities.Game;
import org.example.mappers.GameMapper;
import org.example.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    private GameService gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameRepository, gameMapper);
    }

    @Test
    public void save_assignsUuidWhenIdIsNull() {
        GameDto dto = new GameDto();
        dto.setTitel("Witcher");
        dto.setPrice(49.99);

        Game game = new Game("generated");
        GameDto returned = new GameDto();
        returned.setId("generated");

        when(gameMapper.toDomain(any())).thenReturn(game);
        when(gameMapper.toDto(game)).thenReturn(returned);

        gameService.save(dto);

        assertNotNull(dto.getId());
        verify(gameRepository).save(game);
    }

    @Test
    public void save_keepsExistingValidId() {
        String existingId = "550e8400-e29b-41d4-a716-446655440000";
        GameDto dto = new GameDto();
        dto.setId(existingId);
        dto.setTitel("Witcher");
        dto.setPrice(49.99);

        Game game = new Game(existingId);
        when(gameMapper.toDomain(any())).thenReturn(game);
        when(gameMapper.toDto(game)).thenReturn(dto);

        gameService.save(dto);

        assertEquals(existingId, dto.getId());
    }

    @Test
    public void save_throwsOnNegativePrice() {
        GameDto dto = new GameDto();
        dto.setTitel("Witcher");
        dto.setPrice(-1.0);

        assertThrows(IllegalArgumentException.class, () -> gameService.save(dto));
    }

    @Test
    public void save_throwsOnInvalidTitle() {
        GameDto dto = new GameDto();
        dto.setTitel("Bad@Title#");
        dto.setPrice(10.0);

        assertThrows(IllegalArgumentException.class, () -> gameService.save(dto));
    }

    @Test
    public void save_throwsOnInvalidId() {
        GameDto dto = new GameDto();
        dto.setId("not-a-uuid");
        dto.setTitel("Witcher");
        dto.setPrice(10.0);

        assertThrows(IllegalArgumentException.class, () -> gameService.save(dto));
    }

    @Test
    public void findByName_returnsMappedDto_whenGameExists() {
        Game game = new Game("id-1");
        GameDto dto = new GameDto();
        dto.setId("id-1");
        dto.setTitel("Witcher");

        when(gameRepository.findByTitle("Witcher")).thenReturn(Optional.of(game));
        when(gameMapper.toDto(game)).thenReturn(dto);

        Optional<GameDto> result = gameService.findByName("Witcher");
        // Note: GameRepository.findByTitle needs to use titel column

        assertTrue(result.isPresent());
        assertEquals("id-1", result.get().getId());
    }

    @Test
    public void findByName_returnsEmpty_whenGameNotFound() {
        when(gameRepository.findByTitle("Unknown")).thenReturn(Optional.empty());

        Optional<GameDto> result = gameService.findByName("Unknown");

        assertFalse(result.isPresent());
    }

    @Test
    public void findAll_returnsAllMappedDtos() {
        Game g1 = new Game("id-1");
        Game g2 = new Game("id-2");
        GameDto dto1 = new GameDto();
        dto1.setId("id-1");
        GameDto dto2 = new GameDto();
        dto2.setId("id-2");

        when(gameRepository.findAll()).thenReturn(Arrays.asList(g1, g2));
        when(gameMapper.toDto(g1)).thenReturn(dto1);
        when(gameMapper.toDto(g2)).thenReturn(dto2);

        List<GameDto> result = gameService.findAll();

        assertEquals(2, result.size());
        assertEquals("id-1", result.get(0).getId());
        assertEquals("id-2", result.get(1).getId());
    }

    @Test
    public void findAll_returnsEmptyList_whenNoGames() {
        when(gameRepository.findAll()).thenReturn(Collections.emptyList());

        List<GameDto> result = gameService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void delete_delegatesToRepository() {
        gameService.delete("some-id");

        verify(gameRepository).delete("some-id");
    }

    @Test
    public void findById_returnsMappedDto_whenFound() {
        Game game = new Game("id-1");
        GameDto dto = new GameDto();
        dto.setId("id-1");

        when(gameRepository.findById("id-1")).thenReturn(Optional.of(game));
        when(gameMapper.toDto(game)).thenReturn(dto);

        Optional<GameDto> result = gameService.findById("id-1");

        assertTrue(result.isPresent());
        assertEquals("id-1", result.get().getId());
    }

    @Test
    public void findById_returnsEmpty_whenNotFound() {
        when(gameRepository.findById("missing")).thenReturn(Optional.empty());

        Optional<GameDto> result = gameService.findById("missing");

        assertFalse(result.isPresent());
    }
}
