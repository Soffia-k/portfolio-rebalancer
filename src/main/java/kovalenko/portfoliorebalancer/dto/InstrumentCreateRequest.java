package kovalenko.portfoliorebalancer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kovalenko.portfoliorebalancer.domain.Instrument;

public record InstrumentCreateRequest(
        @NotBlank(message = "ticker must not be blank")
        @Size(min = 1, max = 10, message = "ticker must be between 1 and 10 characters")
        String ticker,

        @NotBlank(message = "name must not be blank")
        @Size(min = 1, max = 100, message = "name must be between 1 and 100 characters")
        String name,

        @NotNull(message = "type must not be null")
        Instrument.Type type
) {}