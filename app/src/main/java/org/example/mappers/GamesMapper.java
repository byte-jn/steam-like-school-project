package org.example.mappers;

import jakarta.inject.Singleton;
import org.example.dtos.GamesDto;
import org.example.entities.Games;

@Singleton
public class GamesMapper {

    public GamesMapper() { }

    public GamesDto toDto(Games game) {
        GamesDto dto = new GamesDto();
        dto.setId(game.getId());
        dto.setTitel(game.getTitel());
        dto.setDescription(game.getDescription());
        dto.setPrice(game.getPrice());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setCreatedAt(game.getCreatedAt());
        return dto;
    }

    public Games toDomain(GamesDto dto) {
        Games game = new Games(dto.getId());
        game.setTitel(dto.getTitel());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setReleaseDate(dto.getReleaseDate());
        return game;
    }
}
