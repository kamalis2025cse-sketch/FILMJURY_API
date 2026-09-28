package com.filmjury.film.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "score_cards",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"entry_id", "judge_id"}
    )
)
public class ScoreCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    private Entry entry;

    @ManyToOne
    @JoinColumn(name = "judge_id", nullable = false)
    private Judge judge;

    private LocalDateTime submittedAt;

    public ScoreCard() {
    }

    @PrePersist
    public void beforeSave() {
        submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Judge getJudge() {
        return judge;
    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
}
