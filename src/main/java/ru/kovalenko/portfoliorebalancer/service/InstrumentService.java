package ru.kovalenko.portfoliorebalancer.service;

import org.springframework.stereotype.Service;
import ru.kovalenko.portfoliorebalancer.domain.Instrument;
import ru.kovalenko.portfoliorebalancer.repository.InstrumentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }

    public Optional<Instrument> findById(Long id) {
        return instrumentRepository.findById(id);
    }

    public List<Instrument> findByType(Instrument.Type type) {
        return instrumentRepository.findByType(type);
    }
}