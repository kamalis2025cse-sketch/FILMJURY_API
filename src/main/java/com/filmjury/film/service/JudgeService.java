package com.filmjury.film.service;

import com.filmjury.film.model.Judge;
import com.filmjury.film.repository.JudgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeService {

    private final JudgeRepository repository;

    public JudgeService(JudgeRepository repository) {
        this.repository = repository;
    }

    public Judge create(Judge judge) {
        return repository.save(judge);
    }

    public List<Judge> getAll() {
        return repository.findAll();
    }

    public Judge getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Judge not found"));
    }

    public Judge update(Long id, Judge updated) {

        Judge judge = getById(id);

        judge.setName(updated.getName());
        judge.setEmail(updated.getEmail());

        return repository.save(judge);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Judge not found");
        }

        repository.deleteById(id);
    }
}
