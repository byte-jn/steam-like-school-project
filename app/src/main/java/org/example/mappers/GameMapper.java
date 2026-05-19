package org.example.mappers;

import jakarta.inject.Singleton;
import org.example.dtos.GameDto;
import org.example.entities.Game;

@Singleton
public class GameMapper {

    public GameMapper() { }

    public GameDto toDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setTitel(game.getTitel());
        dto.setDescription(game.getDescription());
        dto.setPrice(game.getPrice());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setCreatedAt(game.getCreatedAt());
        return dto;
    }

    public Game toDomain(GameDto dto) {
        Game game = new Game(dto.getId());
        game.setTitel(dto.getTitel());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setReleaseDate(dto.getReleaseDate());
        return game;
    }
}
