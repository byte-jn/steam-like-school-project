package org.example.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.dtos.GameDto;
import org.example.entities.Game;

@Singleton
public class GameMapper {

    @Inject
    public GameMapper() { }

    /**
     * Maps a Game domain entity to a GameDto.
     */
    public GameDto toDto(Game game) {
        GameDto dto = new GameDto();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setDescription(game.getDescription());
        dto.setPrice(game.getPrice());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setCreatedAt(game.getCreatedAt());
        return dto;
    }

    /**
     * Maps a GameDto to a Game domain entity.
     * Uses the dto id when present, otherwise generates a new one at the call site.
     */
    public Game toDomain(GameDto dto) {
        Game game = new Game(dto.getId());
        game.setTitle(dto.getTitel());
        game.setDescription(dto.getDescription());
        game.setPrice(dto.getPrice());
        game.setReleaseDate(dto.getReleaseDate());
        return game;
    }
}
