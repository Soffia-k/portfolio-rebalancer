package kovalenko.portfoliorebalancer.service;

import org.springframework.stereotype.Service;
import kovalenko.portfoliorebalancer.domain.Instrument;
import kovalenko.portfoliorebalancer.dto.InstrumentCreateRequest;
import kovalenko.portfoliorebalancer.dto.InstrumentResponse;
import kovalenko.portfoliorebalancer.dto.InstrumentUpdateRequest;
import kovalenko.portfoliorebalancer.exception.BusinessRuleException;
import kovalenko.portfoliorebalancer.exception.NotFoundException;
import kovalenko.portfoliorebalancer.repository.InstrumentRepository;

import java.util.List;

@Service
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public InstrumentService(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    public List<InstrumentResponse> findAll() {
        return instrumentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<InstrumentResponse> findByType(Instrument.Type type) {
        return instrumentRepository.findByType(type).stream()
                .map(this::toResponse)
                .toList();
    }

    public InstrumentResponse findById(Long id) {
        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrument " + id + " not found"));
        return toResponse(instrument);
    }

    public InstrumentResponse create(InstrumentCreateRequest request) {
        if (instrumentRepository.existsByTicker(request.ticker())) {
            throw new BusinessRuleException("Instrument with ticker " + request.ticker() + " already exists");
        }
        Instrument instrument = new Instrument(null, request.ticker(), request.name(), request.type());
        Instrument saved = instrumentRepository.save(instrument);
        return toResponse(saved);
    }

    public InstrumentResponse update(Long id, InstrumentUpdateRequest request) {
        Instrument existing = instrumentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Instrument " + id + " not found"));

        if (request.ticker() != null) {
            boolean tickerTaken = instrumentRepository.existsByTicker(request.ticker())
                    && !existing.getTicker().equalsIgnoreCase(request.ticker());
            if (tickerTaken) {
                throw new BusinessRuleException("Instrument with ticker " + request.ticker() + " already exists");
            }
            existing.setTicker(request.ticker());
        }
        if (request.name() != null) {
            existing.setName(request.name());
        }
        if (request.type() != null) {
            existing.setType(request.type());
        }

        Instrument updated = instrumentRepository.update(id, existing)
                .orElseThrow(() -> new NotFoundException("Instrument " + id + " not found"));
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!instrumentRepository.existsById(id)) {
            throw new NotFoundException("Instrument " + id + " not found");
        }
        instrumentRepository.deleteById(id);
    }

    private InstrumentResponse toResponse(Instrument instrument) {
        return new InstrumentResponse(
                instrument.getId(),
                instrument.getTicker(),
                instrument.getName(),
                instrument.getType()
        );
    }
}