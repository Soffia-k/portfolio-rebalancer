package kovalenko.portfoliorebalancer.dto;

import jakarta.validation.constraints.Size;
import kovalenko.portfoliorebalancer.domain.Instrument;

public record InstrumentUpdateRequest(
        @Size(min = 1, max = 10, message = "ticker must be between 1 and 10 characters")
        String ticker,

        @Size(min = 1, max = 100, message = "name must be between 1 and 100 characters")
        String name,

        Instrument.Type type
) {}