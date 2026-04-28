package org.example.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.dtos.GameDto;
import org.example.entities.Game;
import org.example.mappers.GameMapper;
import org.example.repositories.GameRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Singleton
public class GameService {

    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final Pattern TITLE_PATTERN =
            Pattern.compile("^[A-Za-z0-9 \\-!:'.,&]{1,255}$");

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Inject
    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    private void validate(GameDto dto) {
        if (dto.getId() != null && !UUID_PATTERN.matcher(dto.getId()).matches()) {
            throw new IllegalArgumentException("Invalid id: must be a UUID");
        }
        if (dto.getTitel() != null && !TITLE_PATTERN.matcher(dto.getTitel()).matches()) {
            throw new IllegalArgumentException(
                    "Invalid title: letters, numbers, spaces and basic punctuation only (max 255)");
        }
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
    }

    /**
     * Persists a new game from the given DTO.
     * Generates a UUID if the DTO carries no id.
     */
    public GameDto save(GameDto dto) {
        validate(dto);
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        Game game = gameMapper.toDomain(dto);
        gameRepository.save(game);
        return gameMapper.toDto(game);
    }

    /**
     * Returns a game DTO by id, or empty if not found.
     */
    public Optional<GameDto> findById(String id) {
        return gameRepository.findById(id)
                .map(gameMapper::toDto);
    }

    /**
     * Returns a game DTO by title (case-insensitive), or empty if not found.
     */
    public Optional<GameDto> findByName(String title) {
        return gameRepository.findByTitle(title)
                .map(gameMapper::toDto);
    }

    /**
     * Returns all games as DTOs.
     */
    public List<GameDto> findAll() {
        return gameRepository.findAll()
                .stream()
                .map(gameMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing game from the given DTO.
     */
    public void update(GameDto dto) {
        validate(dto);
        Game game = gameMapper.toDomain(dto);
        gameRepository.update(game);
    }

    /**
     * Deletes a game by id.
     */
    public void delete(String id) {
        gameRepository.delete(id);
    }
}
