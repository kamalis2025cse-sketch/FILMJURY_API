package com.filmjury.film.service;

import com.filmjury.film.model.Criterion;
import com.filmjury.film.repository.CriterionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriterionService {

    private final CriterionRepository repository;

    public CriterionService(CriterionRepository repository) {
        this.repository = repository;
    }

    public Criterion create(Criterion criterion) {
        return repository.save(criterion);
    }

    public List<Criterion> getAll() {
        return repository.findAll();
    }

    public Criterion getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criterion not found"));
    }

    public Criterion update(Long id, Criterion updated) {

        Criterion criterion = getById(id);

        criterion.setName(updated.getName());
        criterion.setMaxScore(updated.getMaxScore());

        return repository.save(criterion);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Criterion not found");
        }

        repository.deleteById(id);
    }
}