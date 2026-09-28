package com.filmjury.film.controller;

import com.filmjury.film.service.LeaderboardService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService service;

    public LeaderboardController(LeaderboardService service) {
        this.service = service;
    }

    @GetMapping
    public List<Map<String, Object>> getLeaderboard() {
        return service.getLeaderboard();
    }
}