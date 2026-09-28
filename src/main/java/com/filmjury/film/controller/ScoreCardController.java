package com.filmjury.film.controller;

import com.filmjury.film.model.ScoreCard;
import com.filmjury.film.service.ScoreCardService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scorecards")
public class ScoreCardController {

    private final ScoreCardService service;

    public ScoreCardController(ScoreCardService service) {
        this.service = service;
    }

    @PostMapping
    public ScoreCard create(
            @RequestParam Long entryId,
            @RequestParam Long judgeId) {

        return service.create(entryId, judgeId);
    }

    @GetMapping
    public List<ScoreCard> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ScoreCard getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/entry/{entryId}")
    public List<ScoreCard> getByEntry(
            @PathVariable Long entryId) {

        return service.getByEntry(entryId);
    }
}