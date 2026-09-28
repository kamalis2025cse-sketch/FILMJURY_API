package com.filmjury.film.controller;

import com.filmjury.film.model.Entry;
import com.filmjury.film.service.EntryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService service;

    public EntryController(EntryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry create(@Valid @RequestBody Entry entry) {
        return service.create(entry);
    }

    @GetMapping
    public List<Entry> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Entry getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Entry update(
            @PathVariable Long id,
            @Valid @RequestBody Entry entry) {

        return service.update(id, entry);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
