package org.example.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import org.example.dtos.UserDto;
import org.example.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller("/users")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Get("/{id}")
    public HttpResponse<UserDto> findById(Long id) {
        Optional<UserDto> found = userService.findById(id);
        if (found.isEmpty()) {
            return HttpResponse.<UserDto>notFound();
        }
        return HttpResponse.ok(found.get());
    }

    @Post
    public HttpResponse<UserDto> create(@Body UserDto dto) {
        return HttpResponse.created(userService.save(dto));
    }

    @Put("/{id}")
    public HttpResponse<UserDto> update(Long id, @Body UserDto dto) {
        Optional<UserDto> updated = userService.update(id, dto);
        if (updated.isEmpty()) {
            return HttpResponse.<UserDto>notFound();
        }
        return HttpResponse.ok(updated.get());
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(Long id) {
        userService.delete(id);
        return HttpResponse.noContent();
    }
}
