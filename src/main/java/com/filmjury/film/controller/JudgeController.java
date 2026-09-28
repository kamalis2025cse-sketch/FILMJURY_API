package com.filmjury.film.controller;

import com.filmjury.film.model.Judge;
import com.filmjury.film.service.JudgeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/judges")
public class JudgeController {

    private final JudgeService service;

    public JudgeController(JudgeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Judge create(@Valid @RequestBody Judge judge) {
        return service.create(judge);
    }

    @GetMapping
    public List<Judge> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Judge getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Judge update(
            @PathVariable Long id,
            @Valid @RequestBody Judge judge) {

        return service.update(id, judge);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
