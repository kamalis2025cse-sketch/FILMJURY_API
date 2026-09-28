package com.filmjury.film.controller;

import com.filmjury.film.model.Criterion;
import com.filmjury.film.service.CriterionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria")
public class CriterionController {

    private final CriterionService service;

    public CriterionController(CriterionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Criterion create(@Valid @RequestBody Criterion criterion) {
        return service.create(criterion);
    }

    @GetMapping
    public List<Criterion> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Criterion getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Criterion update(
            @PathVariable Long id,
            @Valid @RequestBody Criterion criterion) {

        return service.update(id, criterion);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
