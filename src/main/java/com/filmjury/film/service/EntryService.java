package com.filmjury.film.service;

import com.filmjury.film.model.Entry;
import com.filmjury.film.model.ScoreCard;
import com.filmjury.film.repository.EntryRepository;
import com.filmjury.film.repository.ScoreCardRepository;
import com.filmjury.film.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

    private final EntryRepository repository;
    private final ScoreCardRepository scoreCardRepository;
    private final ScoreRepository scoreRepository;

    public EntryService(
            EntryRepository repository,
            ScoreCardRepository scoreCardRepository,
            ScoreRepository scoreRepository) {

        this.repository = repository;
        this.scoreCardRepository = scoreCardRepository;
        this.scoreRepository = scoreRepository;
    }

    public Entry create(Entry entry) {
        return repository.save(entry);
    }

    public List<Entry> getAll() {
        return repository.findAll();
    }

    public Entry getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public Entry update(Long id, Entry updated) {
        Entry entry = getById(id);

        entry.setTitle(updated.getTitle());
        entry.setTeamName(updated.getTeamName());
        entry.setEmail(updated.getEmail());
        entry.setVideoUrl(updated.getVideoUrl());

        return repository.save(entry);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Entry not found");
        }

        // Find all scorecards belonging to this entry
        List<ScoreCard> scoreCards =
                scoreCardRepository.findByEntryId(id);

        // Delete scores first
        for (ScoreCard scoreCard : scoreCards) {
            scoreRepository.deleteByScoreCardId(scoreCard.getId());
        }

        // Delete scorecards next
        scoreCardRepository.deleteAll(scoreCards);

        // Finally delete the entry
        repository.deleteById(id);
    }
}