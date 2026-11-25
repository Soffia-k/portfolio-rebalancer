package kovalenko.portfoliorebalancer.dto;

import kovalenko.portfoliorebalancer.domain.Instrument;

public record InstrumentResponse(Long id, String ticker, String name, Instrument.Type type) {}