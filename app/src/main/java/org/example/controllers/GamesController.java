package org.example.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import org.example.dtos.GameDto;
import org.example.services.GameService;

import java.util.List;
import java.util.Optional;

@Controller("/games")
public class GamesController {

    private final GameService gameService;

    @Inject
    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @Get
    public List<GameDto> findAll() {
        return gameService.findAll();
    }

    @Get("/{id}")
    public HttpResponse<GameDto> findById(String id) {
        Optional<GameDto> found = gameService.findById(id);
        if (found.isEmpty()) {
            return HttpResponse.<GameDto>notFound();
        }
        return HttpResponse.ok(found.get());
    }

    @Post
    public HttpResponse<GameDto> create(@Body GameDto dto) {
        return HttpResponse.created(gameService.save(dto));
    }

    @Put("/{id}")
    public HttpResponse<GameDto> update(String id, @Body GameDto dto) {
        Optional<GameDto> updated = gameService.update(id, dto);
        if (updated.isEmpty()) {
            return HttpResponse.<GameDto>notFound();
        }
        return HttpResponse.ok(updated.get());
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(String id) {
        gameService.delete(id);
        return HttpResponse.noContent();
    }
}
