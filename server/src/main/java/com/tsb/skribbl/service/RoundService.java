package com.tsb.skribbl.service;

import com.tsb.skribbl.entity.RoundEntity;
import com.tsb.skribbl.repository.RoundRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoundService {
    private final RoundRepository repo;

    public RoundService(RoundRepository repo) {
        this.repo = repo;
    }

    public List<RoundEntity> listAll() {
        return repo.findAll();
    }

    public void save(RoundEntity round) {
        repo.save(round);
    }

    public RoundEntity get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
