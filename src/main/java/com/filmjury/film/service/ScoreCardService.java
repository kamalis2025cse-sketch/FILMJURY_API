package com.filmjury.film.service;

import com.filmjury.film.model.Entry;
import com.filmjury.film.model.Judge;
import com.filmjury.film.model.ScoreCard;
import com.filmjury.film.repository.EntryRepository;
import com.filmjury.film.repository.JudgeRepository;
import com.filmjury.film.repository.ScoreCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreCardService {

    private final ScoreCardRepository scoreCardRepository;
    private final EntryRepository entryRepository;
    private final JudgeRepository judgeRepository;

    public ScoreCardService(
            ScoreCardRepository scoreCardRepository,
            EntryRepository entryRepository,
            JudgeRepository judgeRepository) {

        this.scoreCardRepository = scoreCardRepository;
        this.entryRepository = entryRepository;
        this.judgeRepository = judgeRepository;
    }

    public ScoreCard create(Long entryId, Long judgeId) {

        if (scoreCardRepository
                .findByEntryIdAndJudgeId(entryId, judgeId)
                .isPresent()) {

            throw new RuntimeException(
                    "This judge has already scored this entry");
        }

        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        Judge judge = judgeRepository.findById(judgeId)
                .orElseThrow(() -> new RuntimeException("Judge not found"));

        ScoreCard scoreCard = new ScoreCard();

        scoreCard.setEntry(entry);
        scoreCard.setJudge(judge);

        return scoreCardRepository.save(scoreCard);
    }

    public List<ScoreCard> getAll() {
        return scoreCardRepository.findAll();
    }

    public ScoreCard getById(Long id) {
        return scoreCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ScoreCard not found"));
    }

    public List<ScoreCard> getByEntry(Long entryId) {
        return scoreCardRepository.findByEntryId(entryId);
    }
}
