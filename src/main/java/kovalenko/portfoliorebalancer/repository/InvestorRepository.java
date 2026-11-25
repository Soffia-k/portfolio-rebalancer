package kovalenko.portfoliorebalancer.repository;

import org.springframework.stereotype.Repository;
import kovalenko.portfoliorebalancer.domain.Investor;

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

    public Optional<Investor> update(Long id, Investor updated) {
        if (!storage.containsKey(id)) {
            return Optional.empty();
        }
        updated.setId(id);
        storage.put(id, updated);
        return Optional.of(updated);
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}