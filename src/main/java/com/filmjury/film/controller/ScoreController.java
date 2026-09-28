package com.filmjury.film.controller;

import com.filmjury.film.model.Score;
import com.filmjury.film.service.ScoreService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService service;

    public ScoreController(ScoreService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Score create(
            @RequestParam Long scoreCardId,
            @RequestParam Long criterionId,
            @RequestParam Integer marks) {

        return service.create(scoreCardId, criterionId, marks);
    }

    @GetMapping
    public List<Score> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Score getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/scorecard/{scoreCardId}")
    public List<Score> getByScoreCard(
            @PathVariable Long scoreCardId) {

        return service.getByScoreCard(scoreCardId);
    }
}
