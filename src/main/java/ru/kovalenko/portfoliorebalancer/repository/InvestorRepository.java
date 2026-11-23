package ru.kovalenko.portfoliorebalancer.repository;

import org.springframework.stereotype.Repository;
import ru.kovalenko.portfoliorebalancer.domain.Investor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InvestorRepository {

    private final ConcurrentHashMap<Long, Investor> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public InvestorRepository() {
        save(new Investor(null, "Jane Doe"));
        save(new Investor(null, "Kate Smith"));
        save(new Investor(null, "Caroline Green"));
    }

    public Investor save(Investor investor) {
        long id = idCounter.incrementAndGet();
        investor.setId(id);
        storage.put(id, investor);
        return investor;
    }

    public List<Investor> findAll() {
        return List.copyOf(storage.values());
    }

    public Optional<Investor> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
}