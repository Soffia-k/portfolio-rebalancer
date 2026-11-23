package ru.kovalenko.portfoliorebalancer.repository;

import org.springframework.stereotype.Repository;
import ru.kovalenko.portfoliorebalancer.domain.Instrument;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InstrumentRepository {

    private final ConcurrentHashMap<Long, Instrument> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public InstrumentRepository() {
        save(new Instrument(null, "AAPL", "Apple Inc.",          Instrument.Type.STOCK));
        save(new Instrument(null, "MSFT", "Microsoft Corp.",     Instrument.Type.STOCK));
        save(new Instrument(null, "BND",  "Vanguard Total Bond", Instrument.Type.BOND));
    }

    public Instrument save(Instrument instrument) {
        long id = idCounter.incrementAndGet();
        instrument.setId(id);
        storage.put(id, instrument);
        return instrument;
    }

    public List<Instrument> findAll() {
        return List.copyOf(storage.values());
    }

    public Optional<Instrument> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Instrument> findByType(Instrument.Type type) {
        return storage.values().stream()
                .filter(i -> i.getType() == type)
                .toList();
    }
}