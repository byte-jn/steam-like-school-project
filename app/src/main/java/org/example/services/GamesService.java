package org.example.services;

import org.example.dtos.GamesDto;
import org.example.entities.Games;
import org.example.mappers.GamesMapper;
import org.example.repositories.GamesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GamesService {

    private final GamesRepository gamesRepository;
    private final GamesMapper gamesMapper;

    public GamesService(GamesRepository gamesRepository, GamesMapper gamesMapper) {
        this.gamesRepository = gamesRepository;
        this.gamesMapper = gamesMapper;
    }

    /**
     * Persists a new game from the given DTO.
     * Generates a UUID if the DTO carries no id.
     */
    public GamesDto save(GamesDto dto) {
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
