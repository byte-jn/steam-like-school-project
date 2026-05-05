package org.example.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.example.dtos.GamesDto;
import org.example.entities.Games;
import org.example.mappers.GamesMapper;
import org.example.repositories.GamesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class GamesService {

    private final GamesRepository gamesRepository;
    private final GamesMapper gamesMapper;

    @Inject
    public GamesService(GamesRepository gamesRepository, GamesMapper gamesMapper) {
        this.gamesRepository = gamesRepository;
        this.gamesMapper = gamesMapper;
    }

    public GamesDto save(GamesDto dto) {
        if (dto.getId() == null || dto.getId().isEmpty()) {
            dto.setId(UUID.randomUUID().toString());
        }
        Games game = gamesMapper.toDomain(dto);
        return gamesMapper.toDto(gamesRepository.save(game));
    }

    public Optional<GamesDto> findById(String id) {
        return gamesRepository.findById(id).map(gamesMapper::toDto);
    }

    public List<GamesDto> findAll() {
        return gamesRepository.findAll().stream()
                .map(gamesMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<GamesDto> update(String id, GamesDto dto) {
        Optional<Games> found = gamesRepository.findById(id);
        if (found.isEmpty()) {
            return Optional.empty();
        }
        Games existing = found.get();
        existing.setTitel(dto.getTitel());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setReleaseDate(dto.getReleaseDate());
        return Optional.of(gamesMapper.toDto(gamesRepository.update(existing)));
    }

    public void delete(String id) {
        gamesRepository.deleteById(id);
    }
}
