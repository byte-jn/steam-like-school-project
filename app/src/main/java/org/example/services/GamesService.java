package org.example.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.dtos.GamesDto;
import org.example.entities.Games;
import org.example.mappers.GamesMapper;
import org.example.repositories.GamesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Singleton
public class GamesService {

    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final Pattern TITLE_PATTERN =
            Pattern.compile("^[A-Za-z0-9 \\-!:'.,&]{1,255}$");

    private final GamesRepository gamesRepository;
    private final GamesMapper gamesMapper;

    @Inject
    public GamesService(GamesRepository gamesRepository, GamesMapper gamesMapper) {
        this.gamesRepository = gamesRepository;
        this.gamesMapper = gamesMapper;
    }

    private void validate(GamesDto dto) {
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
    public GamesDto save(GamesDto dto) {
        validate(dto);
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        Games game = gamesMapper.toDomain(dto);
        gamesRepository.save(game);
        return gamesMapper.toDto(game);
    }

    /**
     * Returns a game DTO by id, or empty if not found.
     */
    public Optional<GamesDto> findById(String id) {
        return gamesRepository.findById(id)
                .map(gamesMapper::toDto);
    }

    /**
     * Returns all games as DTOs.
     */
    public List<GamesDto> findAll() {
        return gamesRepository.findAll()
                .stream()
                .map(gamesMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing game from the given DTO.
     */
    public void update(GamesDto dto) {
        validate(dto);
        Games game = gamesMapper.toDomain(dto);
        gamesRepository.update(game);
    }

    /**
     * Deletes a game by id.
     */
    public void delete(String id) {
        gamesRepository.delete(id);
    }
}
