package com.filmjury.film.service;

import com.filmjury.film.model.Criterion;
import com.filmjury.film.model.Score;
import com.filmjury.film.model.ScoreCard;
import com.filmjury.film.repository.CriterionRepository;
import com.filmjury.film.repository.ScoreCardRepository;
import com.filmjury.film.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final ScoreCardRepository scoreCardRepository;
    private final CriterionRepository criterionRepository;

    public ScoreService(
            ScoreRepository scoreRepository,
            ScoreCardRepository scoreCardRepository,
            CriterionRepository criterionRepository) {

        this.scoreRepository = scoreRepository;
        this.scoreCardRepository = scoreCardRepository;
        this.criterionRepository = criterionRepository;
    }

    public Score create(Long scoreCardId, Long criterionId, Integer marks) {

        ScoreCard scoreCard = scoreCardRepository.findById(scoreCardId)
                .orElseThrow(() -> new RuntimeException("ScoreCard not found"));

        Criterion criterion = criterionRepository.findById(criterionId)
                .orElseThrow(() -> new RuntimeException("Criterion not found"));

        if (marks < 0 || marks > criterion.getMaxScore()) {
            throw new RuntimeException(
                    "Marks must be between 0 and " + criterion.getMaxScore());
        }

        Score score = new Score();

        score.setScoreCard(scoreCard);
        score.setCriterion(criterion);
        score.setMarks(marks);

        return scoreRepository.save(score);
    }

    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public List<Score> getByScoreCard(Long scoreCardId) {
        return scoreRepository.findByScoreCardId(scoreCardId);
    }

    public Score getById(Long id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Score not found"));
    }
}
