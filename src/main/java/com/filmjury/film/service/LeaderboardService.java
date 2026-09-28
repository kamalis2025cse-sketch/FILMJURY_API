package com.filmjury.film.service;

import com.filmjury.film.model.Entry;
import com.filmjury.film.model.Score;
import com.filmjury.film.model.ScoreCard;
import com.filmjury.film.repository.EntryRepository;
import com.filmjury.film.repository.ScoreCardRepository;
import com.filmjury.film.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaderboardService {

    private final EntryRepository entryRepository;
    private final ScoreCardRepository scoreCardRepository;
    private final ScoreRepository scoreRepository;

    public LeaderboardService(
            EntryRepository entryRepository,
            ScoreCardRepository scoreCardRepository,
            ScoreRepository scoreRepository) {

        this.entryRepository = entryRepository;
        this.scoreCardRepository = scoreCardRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<Map<String, Object>> getLeaderboard() {

        List<Entry> entries = entryRepository.findAll();

        List<Map<String, Object>> leaderboard = new ArrayList<>();

        for (Entry entry : entries) {

            List<ScoreCard> scoreCards =
                    scoreCardRepository.findByEntryId(entry.getId());

            double totalScore = 0;
            int judgeCount = 0;

            for (ScoreCard scoreCard : scoreCards) {

                List<Score> scores =
                        scoreRepository.findByScoreCardId(scoreCard.getId());

                double judgeTotal = 0;

                for (Score score : scores) {
                    judgeTotal += score.getMarks();
                }

                if (!scores.isEmpty()) {
                    totalScore += judgeTotal;
                    judgeCount++;
                }
            }

            double averageScore = 0;

            if (judgeCount > 0) {
                averageScore = totalScore / judgeCount;
            }

            Map<String, Object> result = new LinkedHashMap<>();

            result.put("entryId", entry.getId());
            result.put("title", entry.getTitle());
            result.put("teamName", entry.getTeamName());
            result.put("averageScore", averageScore);

            leaderboard.add(result);
        }

        leaderboard.sort(
        Comparator.comparing(
                (Map<String, Object> item) -> (Double) item.get("averageScore")
        ).reversed()
);

        return leaderboard;
    }
}
