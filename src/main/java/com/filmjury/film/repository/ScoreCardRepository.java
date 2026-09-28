package com.filmjury.film.repository;

import com.filmjury.film.model.ScoreCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {

    Optional<ScoreCard> findByEntryIdAndJudgeId(Long entryId, Long judgeId);

    List<ScoreCard> findByEntryId(Long entryId);
}
