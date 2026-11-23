package ru.kovalenko.portfoliorebalancer.repository;

import org.springframework.stereotype.Repository;
import ru.kovalenko.portfoliorebalancer.domain.Portfolio;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PortfolioRepository {

    private final ConcurrentHashMap<Long, Portfolio> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public PortfolioRepository() {
        save(new Portfolio(null, 1L, "Consevative",   6.0));
        save(new Portfolio(null, 2L, "Balanced", 4.0));
        save(new Portfolio(null, 3L, "Agressive", 3.0));
    }

    public Portfolio save(Portfolio portfolio) {
        long id = idCounter.incrementAndGet();
        portfolio.setId(id);
        storage.put(id, portfolio);
        return portfolio;
    }

    public List<Portfolio> findAll() {
        return List.copyOf(storage.values());
    }

    public Optional<Portfolio> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Portfolio> findByInvestorId(Long investorId) {
        return storage.values().stream()
                .filter(p -> investorId.equals(p.getInvestorId()))
                .toList();
    }
}