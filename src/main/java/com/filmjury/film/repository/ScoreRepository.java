package com.filmjury.film.repository;

import com.filmjury.film.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByScoreCardId(Long scoreCardId);

    void deleteByScoreCardId(Long scoreCardId);
}
