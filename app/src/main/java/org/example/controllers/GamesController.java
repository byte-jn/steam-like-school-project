package org.example.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import org.example.dtos.GamesDto;
import org.example.services.GamesService;

import java.util.List;
import java.util.Optional;

@Controller("/games")
public class GamesController {

    private final GamesService gamesService;

    @Inject
    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @Get
    public List<GamesDto> findAll() {
        return gamesService.findAll();
    }

    @Get("/{id}")
    public HttpResponse<GamesDto> findById(String id) {
        Optional<GamesDto> found = gamesService.findById(id);
        if (found.isEmpty()) {
            return HttpResponse.<GamesDto>notFound();
        }
        return HttpResponse.ok(found.get());
    }

    @Post
    public HttpResponse<GamesDto> create(@Body GamesDto dto) {
        return HttpResponse.created(gamesService.save(dto));
    }

    @Put("/{id}")
    public HttpResponse<GamesDto> update(String id, @Body GamesDto dto) {
        Optional<GamesDto> updated = gamesService.update(id, dto);
        if (updated.isEmpty()) {
            return HttpResponse.<GamesDto>notFound();
        }
        return HttpResponse.ok(updated.get());
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(String id) {
        gamesService.delete(id);
        return HttpResponse.noContent();
    }
}
