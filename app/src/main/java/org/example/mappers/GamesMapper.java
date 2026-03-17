package org.example.mappers;

import org.example.dtos.GamesDto;
import org.example.entities.Games;

public class GamesMapper {

    /**
     * Maps a Games domain entity to a GamesDto.
     */
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

    /**
     * Maps a GamesDto to a Games domain entity.
     * Uses the dto id when present, otherwise generates a new one at the call site.
     */
    public Games toDomain(GamesDto dto) {
        Games game = new Games(dto.getId());
        game.setTitel(dto.getTitel());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setReleaseDate(dto.getReleaseDate());
        return game;
    }
}
