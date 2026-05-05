package org.example.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import org.example.dtos.DlcDto;
import org.example.services.DlcService;

import java.util.List;
import java.util.Optional;

@Controller("/dlcs")
public class DlcController {

    private final DlcService dlcService;

    @Inject
    public DlcController(DlcService dlcService) {
        this.dlcService = dlcService;
    }

    @Get
    public List<DlcDto> findAll() {
        return dlcService.findAll();
    }

    @Get("/{id}")
    public HttpResponse<DlcDto> findById(String id) {
        Optional<DlcDto> found = dlcService.findById(id);
        if (found.isEmpty()) {
            return HttpResponse.<DlcDto>notFound();
        }
        return HttpResponse.ok(found.get());
    }

    @Post
    public HttpResponse<DlcDto> create(@Body DlcDto dto) {
        return HttpResponse.created(dlcService.save(dto));
    }

    @Put("/{id}")
    public HttpResponse<DlcDto> update(String id, @Body DlcDto dto) {
        Optional<DlcDto> updated = dlcService.update(id, dto);
        if (updated.isEmpty()) {
            return HttpResponse.<DlcDto>notFound();
        }
        return HttpResponse.ok(updated.get());
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(String id) {
        dlcService.delete(id);
        return HttpResponse.noContent();
    }
}
